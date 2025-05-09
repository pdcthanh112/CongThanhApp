'use client';

import React, { useCallback, useEffect, useMemo, useState } from 'react';
import { useRouter, useSearchParams } from 'next/navigation';
import BreadcrumbComponent from '@/components/Breadcrumb/Breadcrumb';
import { Breadcrumb, ProductFilter } from '@/models/types';
import { useInfiniteQuery, useQuery } from '@tanstack/react-query';
import { getAllProduct } from '@/api/productApi';
import { Button, Checkbox, Input } from '@/components/ui';
import ShowListProduct from '@/components/Product/ShowListProduct';
import ProductItemCardSkeleton from '@/components/Product/ProductItemCard/ProductItemCardSkeleton';
import { Rate } from 'antd';
import useDebounce from '@/hooks/useDebounce';
import { CATEGORY_KEY } from '@/utils/constants/queryKey';
import request from 'graphql-request';
import { gql } from '@apollo/client';

const crumb: Breadcrumb[] = [
  { pageName: 'Home', url: '/home' },
  { pageName: 'Product', url: '/product' },
];

export default function ProductPage() {
  const router = useRouter();
  const searchParams = useSearchParams();

  const [showAllCategory, setShowAllCategory] = useState<boolean>(false);
  const [showAllBrand, setShowAllBrand] = useState<boolean>(false);

  const initFiltersFromUrl = (): ProductFilter => {
    const params: ProductFilter = {};

    if (searchParams.has('keyword')) params.keyword = searchParams.get('keyword') || undefined;
    // if (searchParams.has('category')) params.category = searchParams.get('category') || undefined;
    if (searchParams.has('category')) {
      const categoryValues = searchParams.getAll('category');
      params.category = categoryValues.length > 0 ? categoryValues : undefined;
    }
    // if (searchParams.has('brand')) params.brand = searchParams.get('brand') || undefined;
    if (searchParams.has('brand')) {
      const brandValues = searchParams.getAll('brand');
      params.brand = brandValues.length > 0 ? brandValues : undefined;
    }
    if (searchParams.has('rating')) {
      const ratingParam = searchParams.get('rating');
      params.rating = ratingParam ? Number(ratingParam) : undefined;
    }
    if (searchParams.has('startPrice')) {
      const startPriceParam = searchParams.get('startPrice');
      params.startPrice = startPriceParam ? Number(startPriceParam) : undefined;
    }
    if (searchParams.has('endPrice')) {
      const endPriceParam = searchParams.get('endPrice');
      params.endPrice = endPriceParam ? Number(endPriceParam) : undefined;
    }

    return params;
  };

  const { data: categoryList } = useQuery<{ id: string; name: string; slug: string }[]>({
    queryKey: [CATEGORY_KEY, 'graphql'],
    queryFn: async () =>
      await request<{ categories: { id: string; name: string; slug: string }[] }>(
        'http://localhost:5002/api/ecommerce/categories/graphql',
        gql`
          query {
            categories {
              id
              name
              slug
            }
          }
        `,
        {
          headers: {
            'Content-Type': 'application/json',
          },
        }
      ).then((response) => response.categories),
  });

  const { data: brandList } = useQuery<{ id: string; name: string; slug: string }[]>({
    queryKey: ['brand', 'graphql'],
    queryFn: async () =>
      await request<{ brands: { id: string; name: string; slug: string }[] }>(
        'http://localhost:5002/api/ecommerce/categories/graphql',
        gql`
          query {
            brands {
              id
              name
              slug
            }
          }
        `,
        {
          headers: {
            'Content-Type': 'application/json',
          },
        }
      ).then((response) => response.brands),
  });

  const initialPagination = useMemo(() => ({ page: 1, limit: 10 }), []);
  const [filters, setFilters] = useState<ProductFilter>(initFiltersFromUrl());

  const [searchKeyword, setSearchKeyword] = useState<string>(filters.keyword || '');
  const [priceRange, setPriceRange] = useState({
    startPrice: filters.startPrice || '',
    endPrice: filters.endPrice || '',
  });

  const debouncedSearchTerm = useDebounce(searchKeyword, 500);
  const debouncedStartPrice = useDebounce(priceRange.startPrice, 500);
  const debouncedEndPrice = useDebounce(priceRange.endPrice, 500);

  const updateUrlWithFilters = (newFilters: ProductFilter) => {
    const params = new URLSearchParams();

    Object.entries(newFilters).forEach(([key, value]) => {
      if (value !== undefined && value !== '') {
        if (Array.isArray(value)) {
          // Nếu giá trị là mảng (category hoặc brand)
          value.forEach((item) => {
            params.append(key, item);
          });
          //chỗ này xử lý attribute
        } else {
          params.set(key, String(value));
        }
      }
    });

    const newUrl = `/product?${params.toString()}`;
    router.push(newUrl);
    // window.history.pushState({}, '', newUrl);
  };

  // Effect để cập nhật filters từ các giá trị debounced
  useEffect(() => {
    const newFilters = { ...filters };

    if (debouncedSearchTerm !== filters.keyword) {
      newFilters.keyword = debouncedSearchTerm || undefined;
    }

    setFilters(newFilters);
    updateUrlWithFilters(newFilters);
  }, [debouncedSearchTerm]);

  useEffect(() => {
    const newFilters = { ...filters };
    let shouldUpdate = false;

    if (debouncedStartPrice !== '' && debouncedStartPrice !== filters.startPrice) {
      newFilters.startPrice = Number(debouncedStartPrice);
      shouldUpdate = true;
    }

    if (debouncedEndPrice !== '' && debouncedEndPrice !== filters.endPrice) {
      newFilters.endPrice = Number(debouncedEndPrice);
      shouldUpdate = true;
    }
    if (shouldUpdate) {
      setFilters(newFilters);
      updateUrlWithFilters(newFilters);
    }
  }, [debouncedStartPrice, debouncedEndPrice]);

  const {
    data: listProduct,
    isLoading,
    fetchNextPage,
    isFetchingNextPage,
    hasNextPage,
  } = useInfiniteQuery({
    queryKey: ['product', filters],
    queryFn: async ({ pageParam = 1 }) => {
      return await getAllProduct({ ...initialPagination, page: pageParam }, filters).then(
        (response) => response.data.responseList
      );
    },
    initialPageParam: 1,
    getNextPageParam: (lastPage, pages) => (lastPage.length > 0 ? pages.length + 1 : undefined),
  });

  // const handleFilter = (key: string, value: string | number) => {
  //   // setPagination({...pagination, page: 0})
  //   router.push({
  //     pathname: '/product',
  //     query: {
  //       ...filters,
  //       [key]: value,
  //     },
  //   });
  // };

  const handleRatingFilter = (rating: number) => {
    const newFilters = { ...filters, rating };
    setFilters(newFilters);
    updateUrlWithFilters(newFilters);
  };

  const handleCategoryFilter = (category: string) => {
    const currentCategories = filters.category || [];
    let newCategories: string[];

    if (currentCategories.includes(category)) {
      newCategories = currentCategories.filter((item) => item !== category);
    } else {
      newCategories = [...currentCategories, category];
    }

    const newFilters = {
      ...filters,
      category: newCategories.length > 0 ? newCategories : undefined,
    };

    setFilters(newFilters);
    updateUrlWithFilters(newFilters);
  };

  const handleBrandFilter = (brand: string) => {
    const currentBrands = filters.brand || [];
    let newBrands: string[];

    if (currentBrands.includes(brand)) {
      newBrands = currentBrands.filter((item) => item !== brand);
    } else {
      newBrands = [...currentBrands, brand];
    }

    const newFilters = {
      ...filters,
      brand: newBrands.length > 0 ? newBrands : undefined,
    };

    setFilters(newFilters);
    updateUrlWithFilters(newFilters);
  };

  const handleClearFilter = () => {
    setFilters({});
    setSearchKeyword('');
    setPriceRange({ startPrice: '', endPrice: '' });
    router.push('/product');
  };

  const handleLoadMore = useCallback(() => {
    if (hasNextPage && !isFetchingNextPage) {
      fetchNextPage();
    }
  }, [fetchNextPage, hasNextPage, isFetchingNextPage]);

  if (isLoading || !listProduct) {
    return <div>Loading...</div>;
  }

  return (
    <React.Fragment>
      <BreadcrumbComponent items={crumb} />
      <div className="grid grid-cols-3 md:grid-cols-6 lg:grid-cols-9 xl:grid-cols-12 gap-4 w-[90%] mx-auto mt-5">
        <div className="col-span-2 p-2">
          Filter
          <Input
            value={searchKeyword}
            placeholder="Search products..."
            onChange={(e) => setSearchKeyword(e.target.value)}
          />
          <div>
            <div className="font-semibold">Category</div>
            {categoryList?.slice(0, showAllCategory ? -1 : 10).map((item) => (
              <div key={item.id}>
                <Checkbox className="mr-2" value={item.name} onCheckedChange={() => handleCategoryFilter(item.slug)} />
                {item.name}
              </div>
            ))}
            <div className="flex justify-end">
              <span
                className="hover:cursor-pointer hover:underline italic"
                onClick={() => setShowAllCategory(!showAllCategory)}
              >
                {showAllCategory ? 'Show less' : 'Show more'}
              </span>
            </div>
          </div>
          <div>
            <div className="font-semibold">Brand</div>
            {brandList?.slice(0, showAllBrand ? -1 : 10).map((item) => (
              <div key={item.id}>
                <Checkbox className="mr-2" value={item.name} onCheckedChange={() => handleBrandFilter(item.slug)} />
                {item.name}
              </div>
            ))}
            <div className="flex justify-end">
              <span
                className="hover:cursor-pointer hover:underline italic"
                onClick={() => setShowAllBrand(!showAllBrand)}
              >
                {showAllBrand ? 'Show less' : 'Show more'}
              </span>
            </div>
          </div>
          <div className="mb-4">
            <span className="block mb-2">Rating</span>
            {[5, 4, 3, 2, 1].map((star) => (
              <div
                key={star}
                className={`cursor-pointer mb-1 ${filters.rating === star ? 'bg-gray-100 rounded p-1' : ''}`}
                onClick={() => handleRatingFilter(star)}
              >
                <Rate value={star} disabled />
                <span className="ml-2">{star} stars</span>
              </div>
            ))}
          </div>
          <div className="mb-4">
            <span className="block mb-2">Price</span>
            <Input
              placeholder="From"
              type="number"
              className="w-full mb-2"
              value={priceRange.startPrice}
              onChange={(e) => setPriceRange({ ...priceRange, startPrice: e.target.value })}
            />
            <Input
              placeholder="To"
              type="number"
              className="w-full"
              value={priceRange.endPrice}
              onChange={(e) => setPriceRange({ ...priceRange, endPrice: e.target.value })}
            />
          </div>
          <Button className="w-full hover:cursor-pointer" onClick={handleClearFilter}>
            Clear filter
          </Button>
        </div>

        <div className="col-span-10">
          {listProduct.pages.map((products, idx) => (
            <ShowListProduct listProduct={products} loading={isLoading} key={idx} />
          ))}
          {isFetchingNextPage &&
            Array.from(Array(10)).map((_, id) => (
              <div
                key={`loading-${id}`}
                className="w-[90%] mx-auto grid gap-4 grid-flow-row-dense grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5"
              >
                <ProductItemCardSkeleton />
              </div>
            ))}
          <Button onClick={handleLoadMore} className="w-full mt-5" disabled={!hasNextPage || isFetchingNextPage}>
            {isFetchingNextPage ? 'Loading more...' : 'Load More'}
          </Button>
        </div>
      </div>
    </React.Fragment>
  );
}

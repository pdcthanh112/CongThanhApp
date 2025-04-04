'use client';

import React, { useCallback, useMemo, useState } from 'react';
import { useRouter } from 'next/navigation';
import BreadcrumbComponent from '@/components/Breadcrumb/Breadcrumb';
import { Breadcrumb } from '@/models/types';
import { useInfiniteQuery } from '@tanstack/react-query';
import { getAllProduct } from '@/api/productApi';
import { Button } from '@/components/ui';
import ShowListProduct from '@/components/Product/ShowListProduct';
import ProductItemCardSkeleton from '@/components/Product/ProductItemCard/ProductItemCardSkeleton';

const crumb: Breadcrumb[] = [
  { pageName: 'Home', url: '/home' },
  { pageName: 'Product', url: '/product' },
];

export default function ProductPage() {
  const router = useRouter();

  // const [pagination, setPagination] = useState<PaginationParams>({ page: 0, limit: 10 });
  const initialPagination = useMemo(() => ({ page: 1, limit: 10 }), []);
  const [filters, setFilters] = useState<any>(null);

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
  //   setPagination({...pagination, page: 0})
  //   router.push({
  //     pathname: '/product',
  //     query: {
  //       ...filters,
  //       [key]: value,
  //     },
  //   });
  // };

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
      <div className="w-[90%] mx-auto">
        {listProduct.pages.map((products, idx) => (
          <ShowListProduct listProduct={products} loading={isLoading} key={idx} />
          // <div
          //   key={index}
          //   className="grid gap-4 grid-flow-row-dense grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5"
          // >
          //   {products.map((product) => (
          //     <ProductItemCard product={product} key={index} />
          //   ))}
          // </div>
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
    </React.Fragment>
  );
}

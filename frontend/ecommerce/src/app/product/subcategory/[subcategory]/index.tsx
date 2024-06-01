import { NextPage } from 'next';
import { useState } from 'react';
import { useParams } from 'next/navigation';
import { useInfiniteQuery, useQuery } from '@tanstack/react-query';
import { getProductBySubcategory } from 'api/productApi';
import { PaginationParams } from '@models/type/Request';
import Pagination from '@components/UI/Pagination';
import ShowListProduct from '@components/Product/ShowListProduct';
import NotFound from './not-found';

const ProductBySubcategory: NextPage = (): React.ReactElement => {
  const param = useParams();
  const subcategory = param?.subcategory;

  const [pagination, setPagination] = useState<PaginationParams>({
    page: 1,
    limit: 10,
    totalPage: 0,
  });

  const { data: listProduct, isLoading } = useQuery({
    queryKey: ['todos', subcategory, pagination],
    queryFn: async () =>
      await getProductBySubcategory(subcategory, pagination.page - 1, pagination.limit).then((result) => {
        setPagination({ ...pagination, totalPage: result.data.totalPage });
        return result.data.responseList;
      }),
  });

  if (listProduct === null || listProduct?.length <= 0) return <NotFound />;

  return (
    <div className="w-[90%] mx-auto">
      <ShowListProduct listProduct={listProduct} loading={isLoading} />

      <Pagination
        count={pagination.totalPage}
        page={pagination.page}
        onChange={(event: React.ChangeEvent<any>, page: number) => {
          setPagination({ ...pagination, page: page });
        }}
      />
    </div>
  );
}

export default ProductBySubcategory;

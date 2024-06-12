export type Response<T> = {
  data: T;
  message: string;
  statusCode: number
}

export type ResponseWithPagination<T> = {
  data: T[];
  paginationInfo: {
    page: number;
    limit: number;
    totalPage: number;
    totalElement: number;
  };
}


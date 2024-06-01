import { PaginationParams } from "@models/type";

export interface ListResponse<T> {
  data: T[];
  pagination: PaginationParams;
}

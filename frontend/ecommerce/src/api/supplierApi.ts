import axiosInstance from '@/config/axiosConfig';
import { Response, Supplier } from '@/models/types';

export const getSupplierById = async (id: any): Promise<Response<Supplier>> => {
  return await axiosInstance.get(`supplier/${id}`).then((response) => response.data);
};

export const getSupplierBySlug = async (slug: string): Promise<Response<Supplier>> => {
  return await axiosInstance.get(`supplier/get-by-slug?slug=${slug}`).then((response) => response.data);
};

export const getProductFromSupplier = async (id: any, page: number, limit: number) => {
  return await axiosInstance
    .get(`supplier/getProductFromSupplier?supplier=${id}&page=${page}&limit=${limit}`)
    .then((response) => response.data);
};

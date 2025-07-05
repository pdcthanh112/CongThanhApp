import axiosInstance from '@/config/axiosConfig';
import { Category, Response } from '@/models/types';

export const getAllCategory = async (page?: number, limit?: number): Promise<Response<Category[]>> => {
  const params = new URLSearchParams();
  page && params.append('page', String(page));
  limit && params.append('limit', String(limit));
  return await axiosInstance.get('categories/list', { params: params }).then((response) => response.data);
};

export const getAllCategoryJson = async () => {
  return await axiosInstance.get('categories').then((response) => response.data);
};

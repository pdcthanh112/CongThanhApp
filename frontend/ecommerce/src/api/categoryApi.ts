import axiosConfig from '@/config/axiosConfig';
import { Category, Response } from '@/models/types';

export const getAllCategory = async (page?: number, limit?: number): Promise<Response<Category[]>> => {
  const params = new URLSearchParams();
  page && params.append('page', String(page));
  limit && params.append('limit', String(limit));
  return await axiosConfig
    .get('categories/', { params: params })
    .then((response) => response.data)
    .catch((error) => {
      throw Error(error);
    });
};

export const getAllCategoryJson = async () => {
  return await axiosConfig
    .get('categories/')
    .then((response) => response.data)
    .catch((error) => {
      throw Error(error);
    });
};

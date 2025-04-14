import axiosInstance from '@/config/axiosConfig';
import { Response, Subcategory } from '@/models/types';

export const getAllSubategory = async (page?: number, limit?: number): Promise<Response<Subcategory[]>> => {
  const params = new URLSearchParams();
  page && params.append('page', String(page));
  limit && params.append('limit', String(limit));
  return await axiosInstance
    .get('subcategory/getAll')
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};

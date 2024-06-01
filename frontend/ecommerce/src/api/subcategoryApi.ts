import axiosConfig from '@/config/axiosConfig';

export const getAllSubategory = async (page?: number, limit?: number) => {
  let url = '';
  page !== undefined && limit !== undefined ? (url = `subcategory/getAll?page=${page}&limit=${limit}`) : (url = 'subcategory/getAll');
  return await axiosConfig
    .get(url)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};

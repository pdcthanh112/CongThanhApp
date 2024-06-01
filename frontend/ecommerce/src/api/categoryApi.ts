import axiosConfig from '@/config/axiosConfig';

export const getAllCategory = async (page?: number, limit?: number) => {
  let url = '';
  page !== undefined && limit !== undefined ? (url = `category/getAll?page=${page}&limit=${limit}`) : (url = `category/getAll`);

  return await axiosConfig
    .get(url)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};

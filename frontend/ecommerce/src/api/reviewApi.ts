import axiosConfig from '@/config/axiosConfig';

export const getRatingStarofProduct = async (productId: string) => {
  return await axiosConfig
    .get(`review/getRatingStar?product=${productId}`)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};

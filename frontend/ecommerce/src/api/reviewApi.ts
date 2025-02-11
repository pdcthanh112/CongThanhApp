import axiosConfig from '@/config/axiosConfig';
import { Response, ResponseWithPagination } from '@/models/types';
import { Review, ReviewStatistic } from '@/models/types/Review';

export const getStatisticFromProduct = async (productId: string): Promise<Response<ReviewStatistic>> => {
  return await axiosConfig
    .get(`review/statistic?product=${productId}`)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};

export const getReviewByProduct = async (productId: string): Promise<ResponseWithPagination<Review>> => {
  return await axiosConfig
    .get(`reviews/store-front/product/${productId}?page=1&limit=5&rating=0`)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};

import axiosInstance from '@/config/axiosConfig';
import { Response, ResponseWithPagination } from '@/models/types';
import { Review, ReviewStatistic } from '@/models/types/Review';

export const getStatisticFromProduct = async (productId: string): Promise<Response<ReviewStatistic>> => {
  return await axiosInstance
    .get(`review/statistic?product=${productId}`)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};

export const getReviewByProduct = async (productId: string, filter: any, pagination: any): Promise<ResponseWithPagination<Review>> => {
  const params = new URLSearchParams();
  filter.hasMedia && params.append('hasMedia', true)
  return await axiosInstance
    .get(`reviews/store-front/product/${productId}?rating=${filter.rating}&page=${pagination.page}&limit=5`, {params})
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};

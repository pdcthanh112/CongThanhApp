import axiosConfig from "@/config/axiosConfig";
import { Response } from "@/models/types";
import { ReviewStatistic } from "@/models/types/Review";

export const getStatisticFromProduct = async (productId: string): Promise<Response<ReviewStatistic>> => {
  return await axiosConfig
    .get(`review/statistic?product=${productId}`)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};

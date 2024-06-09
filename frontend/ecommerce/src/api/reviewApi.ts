import axiosConfig from "@/config/axiosConfig";

export const getStatisticFromProduct = async (productId: string) => {
  return await axiosConfig
    .get(`review/statistic?product=${productId}`)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};

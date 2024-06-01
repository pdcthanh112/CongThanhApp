import axiosConfig from '@/config/axiosConfig';

export const getOrderByStatus = async (status: string, page: number, limit: number) => {
  return await axiosConfig
    .get(`order/getByStatus?status=${status}&page=${page}&limit=${limit}`)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};

// export const getHistoryByCustomer = async (customerId: string) => {
//   return await axiosConfig
//     .get(`purchasing/history?customerId=${customerId}`)
//     .then((response) => response.data)
//     .catch((error) => {
//       throw error;
//     });
// };

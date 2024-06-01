import axiosConfig from '@/config/axiosConfig';

export const getNotificationByCustomer = async (customerId: string) => {
  return await axiosConfig
    .get(`notification/getByCustomer?id=${customerId}`)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};

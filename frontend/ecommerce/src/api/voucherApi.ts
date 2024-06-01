import axiosConfig from '@/config/axiosConfig';

export const getVoucherByCode = async (code: any) => {
  return await axiosConfig
    .get(`voucher/getByCode?code=${code}`)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};

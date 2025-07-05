import axiosInstance from '@/config/axiosConfig';

export const getVoucherByCode = async (code: any) => {
  return await axiosInstance.get(`voucher/getByCode?code=${code}`).then((response) => response.data);
};

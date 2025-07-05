import axiosInstance from '@/config/axiosConfig';
import { OrderDetail } from '@/models/types';

export const getOrderByStatus = async (status: string, page: number, limit: number) => {
  return await axiosInstance
    .get(`orders/getByStatus?status=${status}&page=${page}&limit=${limit}`)
    .then((response) => response.data);
};

export const getOrderDetail = async (orderCode: string): Promise<OrderDetail> => {
  return await axiosInstance.get(`orders/detail/${orderCode}`).then((response) => response.data);
};

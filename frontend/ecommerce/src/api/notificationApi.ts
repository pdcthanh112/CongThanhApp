import axiosInstance from '@/config/axiosConfig';
import { Notification, Response } from '@/models/types';

export const getNotificationByCustomer = async (customerId: string): Promise<Response<Notification[]>> => {
  return await axiosInstance
    .get(`notification/getByCustomer?id=${customerId}`)
    .then((response) => response.data)
    .catch((error) => {
      throw Error(error);
    });
};

export const changeNotificationReadingStatus = async (notificationId: number, status: boolean) => {
  return await axiosInstance
    .patch(`notification/change-read-status/${notificationId}?status=${status}`)
    .then((response) => response.data)
    .catch((error) => {
      throw Error(error);
    });
};

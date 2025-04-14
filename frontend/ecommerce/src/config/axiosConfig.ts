import axios, { AxiosResponse } from 'axios';
import { useSession } from 'next-auth/react';

const axiosInstance = axios.create({
  baseURL: `${process.env.NEXT_PUBLIC_API_URL}/ecommerce/`,
  headers: {
    'Content-Type': 'application/json',
  },
});

axiosInstance.interceptors.request.use(
  async (config) => {
    const { data: session } = useSession();

    if (config.headers) {
      if (!config.headers.Authorization) {
        config.headers['Authorization'] = `Bearer ${session?.token?.accessToken}`;
        // config.headers['Authorization'] = `Bearer ${token}`;
      }
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

axiosInstance.interceptors.response.use(
  (response: AxiosResponse) => {
    return response;
  },
  async (error) => {
    return Promise.reject(error);
  }
);

export default axiosInstance;

import axios, { AxiosResponse } from 'axios';
import { useSession } from 'next-auth/react';
import { useRefreshToken } from '@/hooks/auth/useRefreshToken';

const axiosConfig = axios.create({
  baseURL: `${process.env.NEXT_PUBLIC_API_URL}/ecommerce/`,
  headers: {
    'Content-Type': 'application/json',
  },
});

axiosConfig.interceptors.request.use(
  (config) => {
    // const { data: session } = useSession();
    // if (config.headers) {
    //   if (!config.headers.Authorization) {
    //     config.headers['Authorization'] = `Bearer ${session?.user?.accessToken}`;
    //   }
    // }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

axiosConfig.interceptors.response.use(
  (response: AxiosResponse) => {
    return response;
  },
  async (error) => {
    const { data: session } = useSession();
    const refreshToken = useRefreshToken();
    const prevRequest = error?.config;
    if (error?.response?.status === 401 && !prevRequest?.sent) {
      prevRequest.sent = true;
      await refreshToken();
      prevRequest.headers['Authorization'] = `Bearer ${session?.user?.accessToken}`;
      return axiosConfig(prevRequest);
    }
    return Promise.reject(error);
  }
);

export default axiosConfig;

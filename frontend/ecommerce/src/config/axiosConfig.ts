import axios, { AxiosResponse } from 'axios';

const axiosConfig = axios.create({
  baseURL: `${process.env.NEXT_PUBLIC_API_URL}/ecommerce/`,
  headers: {
    'Content-Type': 'application/json',
  },
});

axiosConfig.interceptors.request.use(
  function (config) {
    if (config.headers) {
      if (!config.headers.Authorization) {
        // const token = store.getState().auth.currentUser?.tokenData.token;
        // if (token) {
        //   config.headers.Authorization = `Bearer ${token}`;
        // }
      }
    }
    return config;
  },
  function (error) {
    return Promise.reject(error);
  },
);

axios.interceptors.response.use(
  function (response: AxiosResponse) {
    return response;
  },
  function (error) {
    return Promise.reject(error);
  },
);

export default axiosConfig;
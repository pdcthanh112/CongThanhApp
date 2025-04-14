import axiosInstance from '@/config/axiosConfig';

export const getSearchHistory = async () => {
  return await axiosInstance
    .get('/search/history')
    .then((response) => response.data)
    .catch((error) => {
      throw Error(error);
    });
};

export const addSearchHistoryItem = async (customerId: string, value: string) => {
  return axiosInstance
    .post('/search/history')
    .then((response) => response.data)
    .catch((error) => {
      throw Error(error);
    });
};

export const removeSearchHistoryItem = async (id: number) => {
  return axiosInstance
    .delete('/search/history')
    .then((response) => response.data)
    .catch((error) => {
      throw Error(error);
    });
};

export const getSearchTrending = async () => {
  return await axiosInstance
    .get('/search/trending')
    .then((response) => response.data)
    .catch((error) => {
      throw Error(error);
    });
};

export const getSearchRecommend = async (keyword: string) => {
  return await axiosInstance
    .get(`/search/recommend?keyword=${keyword}`)
    .then((response) => response.data)
    .catch((error) => {
      throw Error(error);
    });
};

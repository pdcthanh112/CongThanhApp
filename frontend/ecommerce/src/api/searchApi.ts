import axiosConfig from '@/config/axiosConfig';

export const getSearchHistory = async () => {
  return await axiosConfig
    .get('/search/history')
    .then((response) => response.data)
    .catch((error) => {
      throw Error(error);
    });
};

export const addSearchHistoryItem = async (customerId: string, value: string) => {
  return axiosConfig
    .post('/search/history')
    .then((response) => response.data)
    .catch((error) => {
      throw Error(error);
    });
};

export const removeSearchHistoryItem = async (id: number) => {
  return axiosConfig
    .delete('/search/history')
    .then((response) => response.data)
    .catch((error) => {
      throw Error(error);
    });
};

export const getSearchTrending = async () => {
  return await axiosConfig
    .get('/search/trending')
    .then((response) => response.data)
    .catch((error) => {
      throw Error(error);
    });
};

export const getSearchRecommend = async (keyword: string) => {
  return await axiosConfig
    .get(`/search/recommend?keyword=${keyword}`)
    .then((response) => response.data)
    .catch((error) => {
      throw Error(error);
    });
};

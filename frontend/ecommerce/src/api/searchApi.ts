import axiosConfig from '@/config/axiosConfig';

export const getSearchHistory = async () => {
  return await axiosConfig
    .get('/search/history')
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

import axiosInstance from '@/config/axiosConfig';

export const getSearchHistory = async () => {
  return await axiosInstance.get('/search/history').then((response) => response.data);
};

export const addSearchHistoryItem = async (customerId: string, value: string) => {
  return axiosInstance
    .post('/search/history', {
      customer: customerId,
      value,
    })
    .then((response) => response.data);
};

export const removeSearchHistoryItem = async (id: number) => {
  return axiosInstance.delete(`/search/history/${id}`).then((response) => response.data);
};

export const getSearchTrending = async () => {
  return await axiosInstance.get('/search/trending').then((response) => response.data);
};

export const getSearchRecommend = async (keyword: string): Promise<string[]> => {
  // return await axiosInstance
  //   .get(`/search/recommend?keyword=${keyword}`)
  //   .then((response) => response.data)
  //   .catch((error) => {
  //     throw Error(error);
  //   });
  return ['a1', 'a2', 'a3', 'a4', 'aa1', 'aa2', 'ab1', 'ab2'];
};

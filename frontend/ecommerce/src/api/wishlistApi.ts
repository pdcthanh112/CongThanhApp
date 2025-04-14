import axiosInstance from '@/config/axiosConfig';

export const getWishlistByCustomer = async (customerId: string) => {
  return await axiosInstance
    .get(`wishlist/getByCustomer?customerId=${customerId}`)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};

export const addProductToWishlist = async (customerId: string, productId: string) => {
  return await axiosInstance
    .post('wishlist/add', {
      customerId: customerId,
      productId: productId,
    })
    .then((response) => response)
    .catch((error) => {
      throw error;
    });
};

export const removeProductFromWishlist = async (customerId: string, productId: string) => {
  return await axiosInstance
    .delete('wishlist/remove', {
      data: {
        customerId: customerId,
        productId: productId,
      },
    })
    .then((response) => response)
    .catch((error) => {
      throw error;
    });
};

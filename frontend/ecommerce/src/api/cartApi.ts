import axiosConfig from '@/config/axiosConfig';
import { CreateCartForm } from '@/models/form';

export const createNewCart = async (data: CreateCartForm) => {
  return await axiosConfig
    .post('cart/create', {
      name: data.name,
      customer: data.customer,
      isDefault: data.isDefault
    })
    .then((response) => response)
    .catch((error) => {
      throw error;
    });
};

export const deleteCart = async (cartId: String) => {
  return await axiosConfig
    .delete(`cart/${cartId}`)
    .then((response) => response)
    .catch((error) => {
      throw error;
    });
};

export const getCartById = async (id: any) => {
  return await axiosConfig
    .get(`cart/${id}`)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};

export const getCartByCustomerId = async (customerId: string) => {
  return await axiosConfig
    .get(`cart/getByCustomer?customerId=${customerId}`)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};

export const addProductToCart = async ({productId, quantity, cartId}: {productId: string, quantity: number, cartId: string}) => {
  return await axiosConfig
    .post(`cart-item/addToCart?productId=${productId}&quantity=${quantity}&cartId=${cartId}`)
    .then((response) => response)
    .catch((error) => {
      throw error;
    });
};

export const updateCartItem = async (cartItemId: string, quantity: number) => {
  return await axiosConfig
    .put(`cart-item/update?cartItemId=${cartItemId}&quantity=${quantity}`)
    .then((response) => response)
    .catch((error) => {
      throw error;
    });
};

export const deleteCartItem = async (itemId: string) => {
  return await axiosConfig
    .delete(`cart-item/${itemId}`)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};


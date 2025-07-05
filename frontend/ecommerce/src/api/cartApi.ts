import axiosInstance from '@/config/axiosConfig';
import { CreateCartForm } from '@/models/form';
import { Cart, Response } from '@/models/types';

export const createNewCart = async (data: CreateCartForm) => {
  return await axiosInstance
    .post('cart/create', {
      name: data.name,
      customer: data.customer,
      isDefault: data.isDefault,
    })
    .then((response) => response);
};

export const deleteCart = async (cartId: number) => {
  return await axiosInstance.delete(`carts/${cartId}`).then((response) => response);
};

export const getCartById = async (id: number): Promise<Response<Cart>> => {
  return await axiosInstance.get(`carts/${id}`).then((response) => response.data);
};

export const getCartByCustomerId = async (customerId: string): Promise<Response<Cart[]>> => {
  return await axiosInstance.get(`carts/customer/${customerId}`).then((response) => response.data);
};

export const addProductToCart = async ({
  productId,
  quantity,
  cartId,
}: {
  productId: string;
  quantity: number;
  cartId: string;
}) => {
  return await axiosInstance
    .post(`cart-item/addToCart?productId=${productId}&quantity=${quantity}&cartId=${cartId}`)
    .then((response) => response);
};

export const updateCartItem = async ({ cartItemId, quantity }: { cartItemId: number; quantity: number }) => {
  return await axiosInstance
    .put(`cart-item/update?cartItemId=${cartItemId}&quantity=${quantity}`)
    .then((response) => response);
};

export const deleteCartItem = async (itemId: number) => {
  return await axiosInstance.delete(`carts/items/${itemId}`).then((response) => response.data);
};

export const getItemDetail = async ({ cartId, itemId }: { cartId: number; itemId: number }) => {
  return await axiosInstance.get(`carts/${cartId}/items/${itemId}/detail`).then((response) => response.data);
};

import axiosConfig from "@/config/axiosConfig";
import { cache } from "react";

export const getAllProduct = cache(async (page?: number, limit?: number) => {
  const params = new URLSearchParams();
  page && params.append("page", String(page));
  limit && params.append("limit", String(limit));
  return await axiosConfig
    .get("product/getAll")
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
});

export const getProductById = async (productId: string) => {
  return await axiosConfig
    .get(`product/getById/${productId}`)
    .then((response) => response)
    .catch((error) => {
      throw error;
    });
};

export const getProductBySlug = async (productSlug: string) => {
  return await axiosConfig
    .get(`product/getBySlug/${productSlug}`)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};

export const getProductBySubcategory = async (subcategoryId: any, page: number, limit: number) => {
  return await axiosConfig
    .get(`product/getBySubcategory?subcategory=${subcategoryId}&page=${page}&limit=${limit}`)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};

export const getAttributeByProductId = async (productId: string) => {
  return await axiosConfig
    .get(`attribute-value/getByProduct?product=${productId}`)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};

export const getDefaultImageByProductId = async (productId: string) => {
  return await axiosConfig
    .get(`product-image/getDefaultImage?product=${productId}`)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};

export const getImageByProductId = async (productId: string) => {
  return await axiosConfig
    .get(`product-image/getByProduct?product=${productId}`)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};

export const getSoldByProduct = async (productId: string) => {
  return await axiosConfig
    .get(`product/sold/${productId}`)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};
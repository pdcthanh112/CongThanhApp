import axiosInstance from '@/config/axiosConfig';
import {
  ProductVariantAttribute,
  Product,
  ProductImage,
  Response,
  ResponseWithPagination,
  ProductOptionValueGet,
  ProductVariant,
  ProductOptionValueDisplay,
  ProductFilter,
  PaginationParams,
} from '@/models/types';
// import { cacheLife } from 'next/dist/server/use-cache/cache-life';

export const getAllProduct = async (
  pagination: PaginationParams,
  filter: ProductFilter
): Promise<ResponseWithPagination<Product>> => {
  // 'use cache'
  // cacheLife('getAllProduct')
  const params = new URLSearchParams();

  if (filter.category)
    params.append(
      'category',
      filter.category.reduce((item) => item + ',')
    );
  if (filter.brand)
    params.append(
      'brand',
      filter.brand.reduce((item) => item + ',')
    );
  if (filter.category)
    params.append(
      'category',
      filter.category.reduce((item) => item + ',')
    );

  pagination.page && params.append('page', String(pagination.page));
  pagination.limit && params.append('limit', String(pagination.limit));
  return await axiosInstance
    .get('products', { params: params })
    .then((response) => response.data)
    .catch((error) => {
      throw Error(error);
    });
};

export const getProductById = async (productId: string): Promise<Product> => {
  return await axiosInstance
    .get(`product/getById/${productId}`)
    .then((response) => response.data)
    .catch((error) => {
      throw Error(error);
    });
};

export const getProductBySlug = async (productSlug: string): Promise<Response<Product>> => {
  return await axiosInstance
    .get(`products/slug/${productSlug}`)
    .then((response) => response.data)
    .catch((error) => {
      throw Error(error);
    });
};

export const getProductDetailBySlug = async (productSlug: string): Promise<Response<Product>> => {
  return await axiosInstance
    .get(`products/store-front/slug/${productSlug}/detail`)
    .then((response) => response.data)
    .catch((error) => {
      throw Error(error);
    });
};

export const getProductByCategory = async (categoryId: string, page: number, limit: number) => {
  return await axiosInstance
    // .get(`product/getBySubcategory?subcategory=${subcategoryId}&page=${page}&limit=${limit}`)
    .get(`/store-front/categories/${categoryId}/products?page=${page}&limit=${limit}`)
    .then((response) => response.data)
    .catch((error) => {
      throw Error(error);
    });
};

export const getAttributeByProductId = async (productId: string) => {
  return await axiosInstance
    .get(`attribute-value/getByProduct?product=${productId}`)
    .then((response) => response.data)
    .catch((error) => {
      throw Error(error);
    });
};

// export const getDefaultImageByProductId = async (productId: string): Promise<Response<ProductImage>> => {
//   return await axiosInstance
//     .get(`product-image/getDefaultImage?product=${productId}`)
//     .then((response) => response.data)
//     .catch((error) => {
//       throw Error(error);
//     });
// };

export const getImageByProductId = async (productId: string) => {
  return await axiosInstance
    .get(`product-image/getByProduct?product=${productId}`)
    .then((response) => response.data)
    .catch((error) => {
      throw Error(error);
    });
};

export const getSoldByProduct = async (productId: string) => {
  return await axiosInstance
    .get(`product/sold/${productId}`)
    .then((response) => response.data)
    .catch((error) => {
      throw Error(error);
    });
};

export const getVariantAttributeValueByProduct = async (
  productId: string
): Promise<Response<ProductVariantAttribute[]>> => {
  return await axiosInstance
    .get(`product/get-variant-attribute?product=${productId}`)
    .then((response) => response.data)
    .catch((error) => {
      throw Error(error);
    });
};

export const getVariantValueDetail = async (productId: string) => {
  return await axiosInstance
    .get(`products/${productId}/variants/attributes/detail`)
    .then((response) => response.data)
    .catch((error) => {
      throw Error(error);
    });
};

export async function getProductOptionValues(productId: string): Promise<ProductOptionValueGet[]> {
  return await axiosInstance
    .get(`/products/storefront/product-option-combinations/${productId}/values`)
    .then((response) => response.data)
    .catch((error) => {
      throw Error(error);
    });
}

export async function getProductVariationsByParentId(parentId: string): Promise<ProductVariant[]> {
  return await axiosInstance
    .get(`/products/storefront/product-variations/${parentId}`)
    .then((response) => response.data)
    .catch((error) => {
      throw Error(error);
    });
}

export async function getProductOptionValueByProductId(productId: string): Promise<ProductOptionValueDisplay[]> {
  return await axiosInstance
    .get(`/products/storefront/product-option-values/${productId}`)
    .then((response) => response.data)
    .catch((error) => {
      throw Error(error);
    });
}

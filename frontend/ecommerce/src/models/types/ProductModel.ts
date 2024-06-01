export type Product = {
  id: string;
  name: string;
  category: number;
  subcategory: number;
  description: string;
  SKU: string;
  quantity: number;
  image?: string
  price: number;
  production: string;
  status: string;
  slug: string;
  store: string;
}

export type ProductAttribute = {
  id: number;
  name: string;
}

export type AttributeValue = {
  id: number;
  attribute: ProductAttribute;
  product: string;
  value: string;
}

export type ProductImage = {
  id: number;
  product: string;
  imagePath: string;
  alt: string;
  isDefault: boolean;
}

export type Product = {
  id: string;
  name: string;
  category: ProductCategory[];
  slug: string;
  description: string;
  supplier: string;
  thumbnail: ProductImage;
  image: ProductImage[];
  hasVariant: boolean;
  brand: string;
  status: string;
  variant: ProductVariant[];
  attribute: ProductAttribute[];
  metadata: {
    title: string;
    keywords: string;
    description: string;
  };
};

export type ProductCategory = {
  id: string;
  name: string;
  slug: string;
};

export type ProductAttribute = {
  id: number;
  attribute: string;
  value: string;
  product: string;
};

export type ProductImage = {
  id: number;
  product: string;
  imagePath: string;
};

export type ProductVariant = {
  id: string;
  product: string;
  name: string;
  sku: string;
  gtin: string;
  slug: string;
  price: number;
  image: ProductImage[];
  thumbnail: ProductImage;
  options: {
    [key: number]: string;
  };
};

export type ProductOptions = {
  id: number;
  name: string;
  value: string[];
};

export type ProductVariantAttribute = {
  id: number;
  attributeName: string;
  value: [
    {
      id: number;
      value: string;
    }
  ];
};

export type ProductView = {
  id: number;
  productId: string;
  customerId: string;
  viewedAt: Date | number;
};

export type ProductOptionValueGet = {
  id: number;
  productOptionName: string;
  productOptionId: number;
  productOptionValue: string;
};

export type ProductOptionValueDisplay = {
  id: number;
  productOptionId: number;
  productOptionValue: string;
  displayType?: string;
  displayOrder?: number;
  productOptionName: string;
};

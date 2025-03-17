export type Product = {
  id: string;
  name: string;
  category: ProductCategory[];
  slug: string;
  description: string;
  supplier: string;
  image: ProductImage[];
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
  id: string
  name: string
  slug: string
}

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
  alt: string;
};

export type ProductVariant = {
  id: string;
  product: string;
  name: string;
  SKU: string;
  price: number;
  image: ProductVariantImage[];
};

export type ProductVariantImage = {
  id: number;
  variant: string;
  imagePath: string;
  alt: string;
};

export type ProductVariantAttribute = {
  id: number;
  attributeName: string;
  value: [
    {
      id: number;
      value: string;
    },
  ];
};

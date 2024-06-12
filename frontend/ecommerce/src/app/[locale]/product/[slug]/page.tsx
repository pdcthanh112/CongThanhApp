import React, { cache } from 'react';
import { getProductBySlug } from '@/api/productApi';
import { getStatisticFromProduct } from '@/api/reviewApi';
import { Metadata } from 'next';
import ProductDetail from '@/components/Product/ProductDetail';

async function getProductDetail(slug: string) {
  return await getProductBySlug(slug);
}

async function getReviewStatisticByProduct(productId: string) {
  return await getStatisticFromProduct(productId);
}

export async function generateMetadata({ params }: { params: { slug: string } }): Promise<Metadata> {
  const product = await getProductDetail(params.slug);
  return {
    title: product.name,
    description: product.description,
    openGraph: {
      title: product.name,
      description: product.description,
      images: [
        {
          url: product.image,
        },
      ],
    },
    alternates: {
      canonical: process.env.NEXT_PUBLIC_API_URL + '/product/' + params.slug,
    },
  };
}

export default async function ProductDetailPage({ params }: { params: { slug: string } }) {
  const product = await getProductDetail(params.slug).then(res => res.data);
  return (
    <React.Fragment>
      <ProductDetail product={product} />
    </React.Fragment>
  );
}

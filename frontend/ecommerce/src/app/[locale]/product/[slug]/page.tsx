import React from 'react';
import { getProductBySlug } from '@/api/productApi';
import { getStatisticFromProduct } from '@/api/reviewApi';
import { Metadata } from 'next';
import ProductDetail from '@/components/Product/ProductDetail';
import { notFound } from 'next/navigation';
import { getSupplierById } from '@/api/supplierApi';

async function getProductDetail(slug: string) {
  return await getProductBySlug(slug).catch(() => notFound());
}

async function getReviewStatisticByProduct(productId: string) {
  return await getStatisticFromProduct(productId);
}

async function getSupplierInfo(supplierId: string) {
  return await getSupplierById(supplierId);
}

export async function generateMetadata({ params }: { params: { slug: string } }): Promise<Metadata> {
  const product = await getProductDetail(params.slug).then((response) => response.data);
  return {
    title: product.name,
    description: product.description,
    openGraph: {
      title: product.name,
      description: product.description,
      images: [
        {
          url: product.image[0].imagePath,
        },
      ],
    },
    alternates: {
      canonical: process.env.NEXT_PUBLIC_API_URL + '/product/' + params.slug,
    },
  };
}

export default async function ProductDetailPage({ params }: { params: { slug: string } }) {
  const product = await getProductDetail(params.slug).then((response) => response.data);
  const reviewStatistic = await getReviewStatisticByProduct(product.id).then((response) => response.data);
  const supplier = await getSupplierInfo('bf4b47df-cb93-4691-a35e-f4f9044097a6').then((response) => response.data);
  // const supplier = await getSupplierInfo(product.supplier).then((response) => response.data);

  return (
    <React.Fragment>
      <ProductDetail product={product} reviewStatistic={reviewStatistic} supplier={supplier} />
    </React.Fragment>
  );
}

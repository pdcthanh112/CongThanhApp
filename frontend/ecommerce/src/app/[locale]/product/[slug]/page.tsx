import React from "react";
import { getProductBySlug } from "@/api/productApi";
import { getStatisticFromProduct } from "@/api/reviewApi";
import { Metadata } from "next";

async function getProductDetail(slug: string) {
  return await getProductBySlug(slug);
}

async function getReviewStatisticByProduct(productId: string) {
  return await getStatisticFromProduct(productId);
}

export async function generateMetadata({ params }: { params: { slug: string } }): Promise<Metadata> {
  const product = await getProductBySlug(params.slug);
  return {
    title: 'product.title',
    // title: product.title,
    description: product.description,
    openGraph: {
      title: product.title,
      description: product.description,
      images: [
        {
          url: product.image,
        },
      ],
    },
  };
}

export default async function ProductDetailPage({ params: { slug } }: { params: { slug: string } }) {
  console.log("SSSSSSSSSSS", slug);
  return <div>page</div>;
}

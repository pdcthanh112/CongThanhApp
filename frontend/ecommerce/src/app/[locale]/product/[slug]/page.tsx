import React from 'react';
import { getProductBySlug } from '@/api/productApi';
import { getStatisticFromProduct } from '@/api/reviewApi';
import { Metadata } from 'next';
import ProductDetail from '@/components/Product/ProductDetail';
import { notFound } from 'next/navigation';
import { getSupplierById } from '@/api/supplierApi';
import { Product, Supplier } from '@/models/types';
import { ReviewStatistic } from '@/models/types/Review';

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
  // const product = await getProductDetail(params.slug).then((response) => response.data);
  const product: Product = {
    id: '043-103090340-34543',
    name: 'Iphone 16 Pro Max',
    category: "Cateeeeeeeeee",
    slug: 'iphone-16-pro-max',
    description: '<p>iPhone 16 brings you Dynamic Island, a 48MP Main camera, and USB-C—all in a durable color-infused glass and aluminum design.</p>',
    supplier: 'Apple',
    image: [
      {
        imagePath: "https://www.vntousa.org/images/users/images/news/halong.jpg",
        alt: "alt",
        isDefault: true,
        id: 0,
        product: ''
      },
      {
        imagePath: "https://www.mrlinhadventure.com/UserFiles/image/Travel-blog/Image-content/Top-Landscapes-in-Vietnam-1.jpg",
        alt: "alt",
        isDefault: false,
        id: 0,
        product: ''
      },
      {
        imagePath: "https://lilystravelagency.com/wp-content/uploads/2022/10/Tam-Co-Trang-An-1.jpg",
        alt: "alt",
        isDefault: true,
        id: 0,
        product: ''
      },
      {
        imagePath: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTvTZzRBQJr3hGRmgOafgRHzNAvWYXA3RY3_g&s",
        alt: "alt",
        isDefault: true,
        id: 0,
        product: ''
      },
    ],
    brand: '',
    status: '',
    variant: [],
    attribute: [
      {
        id: 0,
        attribute: 'Manufactual',
        value: 'Apple',
        product: ''
      },
      {
        id: 0,
        attribute: 'Material',
        value: 'Steel',
        product: ''
      },
    ]
  }
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
  // const product = await getProductDetail(params.slug).then((response) => response.data);
  const product: Product = {
    id: '043-103090340-34543',
    name: 'Iphone 16 Pro Max',
    category: "Cateeeeeeeeee",
    slug: 'iphone-16-pro-max',
    description: '<p>iPhone 16 brings you Dynamic Island, a 48MP Main camera, and USB-C—all in a durable color-infused glass and aluminum design.</p>',
    supplier: 'Apple',
    image: [
      {
        imagePath: "https://www.vntousa.org/images/users/images/news/halong.jpg",
        alt: "alt",
        isDefault: true,
        id: 0,
        product: ''
      },
      {
        imagePath: "https://www.mrlinhadventure.com/UserFiles/image/Travel-blog/Image-content/Top-Landscapes-in-Vietnam-1.jpg",
        alt: "alt",
        isDefault: false,
        id: 0,
        product: ''
      },
      {
        imagePath: "https://lilystravelagency.com/wp-content/uploads/2022/10/Tam-Co-Trang-An-1.jpg",
        alt: "alt",
        isDefault: true,
        id: 0,
        product: ''
      },
      {
        imagePath: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTvTZzRBQJr3hGRmgOafgRHzNAvWYXA3RY3_g&s",
        alt: "alt",
        isDefault: true,
        id: 0,
        product: ''
      },
    ],
    brand: '',
    status: '',
    variant: [],
    attribute: [
      {
        id: 0,
        attribute: 'Manufactual',
        value: 'Apple',
        product: ''
      },
      {
        id: 0,
        attribute: 'Material',
        value: 'Steel',
        product: ''
      },
    ]
  }
  // const reviewStatistic = await getReviewStatisticByProduct(product.id).then((response) => response.data);
  const reviewStatistic: ReviewStatistic = {
    totalReview: 4646,
    averageRating: 4.7,
    reviewRating5Star: 868,
    reviewRating4Star: 615,
    reviewRating3Star: 486,
    reviewRating2Star: 15,
    reviewRating1Star: 49,
    reviewWithMedia: 1684
  }
  // const supplier = await getSupplierInfo('bf4b47df-cb93-4691-a35e-f4f9044097a6').then((response) => response.data);
  // const supplier = await getSupplierInfo(product.supplier).then((response) => response.data);
  const supplier: Supplier = {
    id: '',
    name: 'CongThanh Bookstore',
    avatar: 'https://upload.wikimedia.org/wikipedia/commons/e/e7/Leucanthemum_vulgare_%27Filigran%27_Flower_2200px.jpg',
    background: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT9CC7j_ay_XBORSQ6s9bk4oJQIBg7uU5f83efxgR-WxFWi27kEFALEoN4qnAZyJVdEGi8&usqp=CAU',
    slug: ''
  }



  return (
    <React.Fragment>
      <ProductDetail product={product} reviewStatistic={reviewStatistic} supplier={supplier} />
    </React.Fragment>
  );
}

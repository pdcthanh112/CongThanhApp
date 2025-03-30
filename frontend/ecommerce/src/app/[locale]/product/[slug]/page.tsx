import React from 'react';
import { Metadata } from 'next';
import { notFound } from 'next/navigation';
import {
  getProductDetailBySlug,
  getProductOptionValueByProductId,
  getProductOptionValues,
  getProductVariationsByParentId,
} from '@/api/productApi';
import ProductDetail from '@/components/Product/ProductDetail';
import { getStatisticFromProduct } from '@/api/reviewApi';
import { getSupplierById } from '@/api/supplierApi';
import { ProductOptions, ProductVariant } from '@/models/types';
import { ReviewStatistic } from '@/models/types/Review';
import RelatedProduct from '@/components/Product/RelatedProduct';
import ReviewProduct from '@/components/Product/ProductDetail/ReviewProduct';
import { getTranslations } from 'next-intl/server';

type PropsType = Promise<{
  slug: string;
  pvid?: string;
}>;

async function getProductDetail(slug: string) {
  return await getProductDetailBySlug(slug).catch(() => notFound());
}

// async function getReviewStatisticByProduct(productId: string) {
//   return await getStatisticFromProduct(productId);
// }

// async function getSupplierInfo(supplierId: string) {
//   return await getSupplierById(supplierId);
// }

const MOCK_REVIEW_STATISTIC: ReviewStatistic = {
    totalReview: 4646,
    averageRating: 4.7,
    reviewRating5Star: 868,
    reviewRating4Star: 615,
    reviewRating3Star: 486,
    reviewRating2Star: 15,
    reviewRating1Star: 49,
    reviewWithMedia: 1684,
};

const fetchAndSortProductVariations = async (productId: string): Promise<ProductVariant[]> => {
    try {
        let productVariations = await getProductVariationsByParentId(productId);
        if (productVariations && productVariations.length > 0) {
            productVariations = productVariations.sort((a, b) => {
                return Object.keys(a.options).length - Object.keys(b.options).length;
            });
        }
        return productVariations;
    } catch (error) {
        console.error(error);
        return [];
    }
};

export async function generateMetadata({ params }: { params: PropsType }): Promise<Metadata> {
  const { slug } = await params;
  const product = await getProductDetail(slug).then((response) => response.data);

  return {
    title: product.name,
    description: product.description,
    openGraph: {
      title: product.name,
      description: product.description,
      images: [
        {
          url: product.thumbnail.imagePath,
        },
      ],
    },
    alternates: {
      canonical: process.env.NEXT_PUBLIC_API_URL + '/product/' + slug,
    },
  };
}

export default async function ProductDetailPage({ params }: { params: PropsType }) {
  const { slug, pvid } = await params;
  const t = await getTranslations();

  const productOptions: ProductOptions[] = [];
  let productVariant: ProductVariant[] = [];

  const product = await getProductDetail(slug).then((response) => response.data);

  if (product.hasVariant) {
    try {
      const productOptionValues = await getProductOptionValues(product.id);
      for (const optionValue of productOptionValues) {
        const index = productOptions.findIndex((productOption) => productOption.name === optionValue.productOptionName);
        if (index > -1) {
          productOptions.at(index)?.value.push(optionValue.productOptionValue);
        } else {
          const newProductOption: ProductOptions = {
            id: optionValue.productOptionId,
            name: optionValue.productOptionName,
            value: [optionValue.productOptionValue],
          };

          productOptions.push(newProductOption);
        }
      }
    } catch (error) {
      console.error(error);
    }

    productVariant = await fetchAndSortProductVariations(product.id);
  }
  const reviewStatistic = MOCK_REVIEW_STATISTIC;

  const productOptionValue = await getProductOptionValueByProductId(product.id);

  return (
    <React.Fragment>
      <ProductDetail
        product={product}
        pvid={pvid !== undefined ? pvid : null}
        reviewStatistic={reviewStatistic}
        productOption={productOptions}
        productVariant={productVariant}
        productOptionValueGet={productOptionValue}
      />

      {/* <div className="bg-white mt-10 p-5">
        <h2 className="bg-yellow-100 px-2 py-1 rounded-sm">{t('product.product_review').toUpperCase()}</h2>
        <ReviewProduct productId={product.id} reviewStatictis={reviewStatistic} />
      </div>

      <RelatedProduct /> */}
    </React.Fragment>
  );
}

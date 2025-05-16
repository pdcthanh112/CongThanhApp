import React from 'react';
import { UseFormReturn } from 'react-hook-form';
import { ProductSchemaType } from '@/models/schema/productSchema';

type Props = {
  form: UseFormReturn<ProductSchemaType>;
};

const TabRelatedProduct = ({ form }: Props) => {
  return <div>TabRelatedProduct</div>;
};

export default TabRelatedProduct;

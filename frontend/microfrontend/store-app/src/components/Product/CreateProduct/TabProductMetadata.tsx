import React from 'react'
import { UseFormReturn } from 'react-hook-form';
import { ProductSchemaType } from '@/models/schema/productSchema';

type Props = { form: UseFormReturn<ProductSchemaType> };

const TabProductMetadata = ({ form }: Props) => {
  return (
    <div>TabProductMetadata</div>
  )
}

export default TabProductMetadata
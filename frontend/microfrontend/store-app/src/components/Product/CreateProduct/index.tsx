'use client';

import React from 'react';
import dynamic from 'next/dynamic';
import { Tabs, type TabsProps } from 'antd';
import { SubmitHandler, useForm } from 'react-hook-form';
import { useTranslations } from 'next-intl';
import { createProductSchema, ProductSchemaType } from '@/models/schema/productSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import { Form } from '@/components/ui/form';
import { Button } from '@/components/ui/button';

const TabProductInfo = dynamic(() => import('./TabProductInfo'), { ssr: false });
const TabProductAttribute = dynamic(() => import('./TabProductAttribute'), { ssr: false });
const TabProductImage = dynamic(() => import('./TabProductImage'), { ssr: false });
const TabProductVariant = dynamic(() => import('./TabProductVariant'), { ssr: false });
const TabProductMetadata = dynamic(() => import('./TabProductMetadata'), { ssr: false });

export default function CreateProduct() {
  const t = useTranslations();
  const ProductSchema = createProductSchema(t);

  const form = useForm<ProductSchemaType>({
    defaultValues: {description: 'aaa'},
    resolver: zodResolver(ProductSchema),
  });

  const onSubmit: SubmitHandler<ProductSchemaType> = (data) => {
    console.log('YYYYYYY', data);
  };

  const items: TabsProps['items'] = [
    {
      key: 'product',
      label: 'Product',
      children: <TabProductInfo form={form} />,
    },
    {
      key: 'attribute',
      label: 'Product Attribute',
      children: <TabProductAttribute form={form} />,
    },
    {
      key: 'image',
      label: 'Product Image',
      children: <TabProductImage form={form} />,
    },
    {
      key: 'variant',
      label: 'Product Variant',
      children: <TabProductVariant form={form} />,
    },
    {
      key: 'metadata',
      label: 'Metadata',
      children: <TabProductMetadata form={form} />,
    },
  ];

  return (
    <React.Fragment>
      <div className="w-4/5 mx-auto mt-3">
        <Form {...form}>
          <form onSubmit={form.handleSubmit(onSubmit)}>
            <Tabs defaultActiveKey="product" items={items} />
            <div className="flex justify-center gap-2 mt-3">
              <Button className="bg-white border-2 border-blue-400 text-blue-500 hover:bg-blue-200">Cancel</Button>
              <Button className="bg-blue-500 hover:bg-blue-700 px-6">Save</Button>
            </div>
          </form>
        </Form>
      </div>
    </React.Fragment>
  );
}

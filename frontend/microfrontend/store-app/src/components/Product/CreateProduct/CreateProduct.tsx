'use client';

import React, { useState } from 'react';
import { Tabs, type TabsProps } from 'antd';
import { SubmitHandler, useForm } from 'react-hook-form';
import { useTranslations } from 'next-intl';
import { createProductSchema, ProductSchemaType } from '@/models/schema/productSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import { Form } from '@/components/ui/form';

export default function CreateProduct() {
  const t = useTranslations();
  const ProductSchema = createProductSchema(t);

  const form = useForm<ProductSchemaType>({
    defaultValues: {},
    resolver: zodResolver(ProductSchema),
  });

  const onSubmit: SubmitHandler<ProductSchemaType> = (data) => {
    console.log(data);
  };

  const items: TabsProps['items'] = [
    {
      key: 'product',
      label: 'Product',
      children: <TabProduct />,
    },
    {
      key: 'attribute',
      label: 'Product Attribute',
      children: 'Content of Tab Pane 1',
    },
    {
      key: 'image',
      label: 'Product Image',
      children: 'Content of Tab Pane 2',
    },
    {
      key: 'variant',
      label: 'Product Variant',
      children: 'Content of Tab Pane 3',
    },
    {
      key: 'metadata',
      label: 'Metadata',
      children: 'Content of Tab Pane 3',
    },
  ];

  return (
    <React.Fragment>
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)}>
          <Tabs defaultActiveKey="1" items={items} />
        </form>
      </Form>
    </React.Fragment>
  );
}

const TabProduct = () => <div>lalsjflaf</div>;

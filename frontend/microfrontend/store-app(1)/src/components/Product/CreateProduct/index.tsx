'use client';

import React, { useState } from 'react';
import dynamic from 'next/dynamic';
import { Steps, Tabs } from 'antd';
import { SubmitHandler, useForm } from 'react-hook-form';
import { useTranslations } from 'next-intl';
import { createProductSchema, ProductSchemaType } from '@/models/schema/productSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import { Form } from '@/components/ui/form';
import { Button } from '@/components/ui/button';
import { ArrowLeft, ArrowRight } from 'lucide-react';
import { Autocomplete, TextField } from '@mui/material';
import { ProductType } from '@/utils/enum';

const TabProductInfo = dynamic(() => import('./TabProductInfo'), { ssr: false });
const TabProductAttribute = dynamic(() => import('./TabProductAttribute'), { ssr: false });
const TabProductImage = dynamic(() => import('./TabProductImage'), { ssr: false });
const TabProductOption = dynamic(() => import('./TabProductOption'), { ssr: false });
const TabProductVariant = dynamic(() => import('./TabProductVariant'), { ssr: false });
const TabProductMetadata = dynamic(() => import('./TabProductMetadata'), { ssr: false });
const TabRelatedProduct = dynamic(() => import('./TabRelatedProduct'), { ssr: false });

export default function CreateProduct() {
  const [productType, setProductType] = useState<ProductType>(ProductType.Single);
  const [current, setCurrent] = useState(0);

  const t = useTranslations();
  const ProductSchema = createProductSchema(t);

  const form = useForm<ProductSchemaType>({
    defaultValues: { description: 'aaa' },
    resolver: zodResolver(ProductSchema),
  });

  const onSubmit: SubmitHandler<ProductSchemaType> = (data) => {
    console.log('YYYYYYY', data);
  };

  const steps = [
    {
      title: 'Product',
      label: 'Product',
      description: 'Add basic information',
      content: <TabProductInfo productType={productType} form={form} />,
    },
    {
      title: 'Product Attribute',
      label: 'Attribute',
      description: 'Add attribute',
      content: <TabProductAttribute form={form} />,
    },
    {
      title: 'Product Image',
      label: 'Media',
      description: 'Add thumbnail & image',
      content: <TabProductImage form={form} />,
    },
    // {
    //   title: 'Product Variant',
    //   description: 'Add variants',
    //   content: <TabProductVariant form={form} />,
    // },
    ...(productType === ProductType.Variable
      ? [
          {
            title: 'Product Option',
            label: 'Option',
            description: 'Add options',
            content: <TabProductOption form={form} />,
          },
        ]
      : []),
    ...(productType === ProductType.Variable
      ? [
          {
            title: 'Product Variant',
            label: 'Variant',
            description: 'Add variants',
            content: <TabProductVariant form={form} />,
          },
        ]
      : []),
    {
      title: 'Metadata',
      label: 'Metadata',
      description: 'Describe metadata',
      content: <TabProductMetadata form={form} />,
    },
    {
      title: 'Related Product',
      label: 'Related Product',
      description: 'Describe metadata',
      content: <TabRelatedProduct form={form} />,
    },
  ];

  const items = steps.map((item) => ({
    key: item.title,
    label: item.label,
    title: item.title,
    description: item.description,
    children: item.content,
  }));

  // const contentStyle: React.CSSProperties = {
  //   minHeight: '500px',
  //   textAlign: 'center',
  //   color: 'black',
  //   backgroundColor: 'white',
  //   borderRadius: 5,
  //   border: `1px solid #333`,
  //   marginTop: 16,
  //   padding: '8px 12px',
  // };

  return (
    <React.Fragment>
      <h3>Add product</h3>

      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)}>
          <Steps size="small" current={current} items={items} onChange={(value) => setCurrent(value)} />

          <Autocomplete
            options={Object.keys(ProductType) as (keyof typeof ProductType)[]}
            renderInput={(params) => <TextField {...params} label="" />}
            defaultValue={productType}
            size="small"
            sx={{ width: 200 }}
            disableClearable
            onChange={(_, value) => setProductType(ProductType[value as keyof typeof ProductType])}
          />
          <Tabs items={items} type="card" tabPosition="left" tabBarStyle={{ width: 200 }} />

          {/* <div style={contentStyle}>{steps[current].content}</div> */}
          <div className="mt-3 flex justify-between">
            <Button type="button" disabled={current <= 0} onClick={() => setCurrent(current - 1)}>
              <ArrowLeft />
              Previous
            </Button>

            <Button type="button" disabled={current >= steps.length - 1} onClick={() => setCurrent(current + 1)}>
              Next
              <ArrowRight />
            </Button>
          </div>

          <div className="flex justify-center gap-2 mt-3">
            <Button className="bg-white border-2 border-blue-400 text-blue-500 hover:bg-blue-200">Cancel</Button>
            <Button className="bg-blue-500 hover:bg-blue-700 px-6">Save</Button>
          </div>
        </form>
      </Form>
    </React.Fragment>
  );
}

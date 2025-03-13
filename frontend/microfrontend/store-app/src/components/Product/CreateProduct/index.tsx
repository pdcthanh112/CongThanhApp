'use client';

import React, { useState } from 'react';
import dynamic from 'next/dynamic';
import { Steps } from 'antd';
import { SubmitHandler, useForm } from 'react-hook-form';
import { useTranslations } from 'next-intl';
import { createProductSchema, ProductSchemaType } from '@/models/schema/productSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import { Form } from '@/components/ui/form';
import { Button } from '@/components/ui/button';
import { ArrowLeft, ArrowRight } from 'lucide-react';

const TabProductInfo = dynamic(() => import('./TabProductInfo'), { ssr: false });
const TabProductAttribute = dynamic(() => import('./TabProductAttribute'), { ssr: false });
const TabProductImage = dynamic(() => import('./TabProductImage'), { ssr: false });
const TabProductVariant = dynamic(() => import('./TabProductVariant'), { ssr: false });
const TabProductMetadata = dynamic(() => import('./TabProductMetadata'), { ssr: false });

export default function CreateProduct() {
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
      description: 'Add basic information',
      content: <TabProductInfo form={form} />,
    },
    {
      title: 'Product Attribute',
      description: 'Add attribute',
      content: <TabProductAttribute form={form} />,
    },
    {
      title: 'Product Image',
      description: 'Add thumbnail & image',
      content: <TabProductImage form={form} />,
    },
    {
      title: 'Product Variant',
      description: 'Add variants',
      content: <TabProductVariant form={form} />,
    },
    {
      title: 'Metadata',
      description: 'Describe metadata',
      content: <TabProductMetadata form={form} />,
    },
  ];

  const items = steps.map((item) => ({ key: item.title, title: item.title, description: item.description }));

  const contentStyle: React.CSSProperties = {
    minHeight: '500px',
    textAlign: 'center',
    color: 'black',
    backgroundColor: 'white',
    borderRadius: 5,
    border: `1px solid #333`,
    marginTop: 16,
    padding: '8px 12px',
  };

  return (
    <React.Fragment>
      <h3>Add product</h3>
      <div className="w-4/5 mx-auto mt-3">
        <Form {...form}>
          <form onSubmit={form.handleSubmit(onSubmit)}>
            <>
              <Steps size="small" current={current} items={items} onChange={(value) => setCurrent(value)} />
              <div style={contentStyle}>{steps[current].content}</div>
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
            </>
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

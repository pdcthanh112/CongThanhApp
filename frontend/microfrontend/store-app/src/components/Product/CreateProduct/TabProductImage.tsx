import React from 'react';
import { UseFormReturn } from 'react-hook-form';
import { ProductSchemaType } from '@/models/schema/productSchema';
import { FormControl, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui';
import { MultiImageUpload } from '@/components/MultiImageUpload';

type Props = { form: UseFormReturn<ProductSchemaType> };

const TabProductImage = ({ form }: Props) => {
  const thumbnail = form.watch('thumbnail') || null;
  const images = form.watch('image') || [];

  return (
    <div>
      <FormField
        control={form.control}
        name="thumbnail"
        render={({ field }) => (
          <FormItem className="h-36 space-y-0">
            <FormLabel>Thumbnail</FormLabel>
            <FormControl {...field}>
              <MultiImageUpload
                value={thumbnail}
                onChange={(thumbnail) => form.setValue('thumbnail', thumbnail[0])}
                maxImages={1}
              />
            </FormControl>
            <FormMessage />
          </FormItem>
        )}
      />

      <FormField
        control={form.control}
        name="image"
        render={({ field }) => (
          <FormItem className="">
            <FormLabel>Image</FormLabel>
            <FormControl {...field} className='w-full h-80 border border-gray-400 p-3 rounded'>
              <MultiImageUpload value={images} onChange={(files) => form.setValue('image', files)} maxImages={20} />
            </FormControl>
            <FormMessage />
          </FormItem>
        )}
      />
    </div>
  );
};

export default TabProductImage;

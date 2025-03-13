import React from 'react';
import { UseFormReturn } from 'react-hook-form';
import { ProductSchemaType } from '@/models/schema/productSchema';
import { FormControl, FormField, FormItem, FormLabel, FormMessage, Input, Textarea } from '@/components/ui';

type Props = { form: UseFormReturn<ProductSchemaType> };

const TabProductMetadata = ({ form }: Props) => {
  return (
    <React.Fragment>
      <FormField
        control={form.control}
        name="metadata.title"
        render={({ field }) => (
          <FormItem className=" mb-3 gap-0.5">
            <FormLabel  style={{ color: 'inherit' }}>Title</FormLabel>
            <FormControl>
              <Input {...field} />
            </FormControl>
            <FormMessage />
          </FormItem>
        )}
      />
      <FormField
        control={form.control}
        name="metadata.keyword"
        render={({ field }) => (
          <FormItem className=" mb-3 gap-0.5">
            <FormLabel style={{ color: 'inherit' }}>Keywords</FormLabel>
            <FormControl>
              <Input {...field} />
            </FormControl>
            <FormMessage />
          </FormItem>
        )}
      />
      <FormField
        control={form.control}
        name="metadata.description"
        render={({ field }) => (
          <FormItem className=" mb-3 gap-0.5">
            <FormLabel style={{ color: 'inherit' }}>Description</FormLabel>
            <FormControl>
              <Textarea  {...field} />
            </FormControl>
            <FormMessage />
          </FormItem>
        )}
      />
    </React.Fragment>
  );
};

export default TabProductMetadata;

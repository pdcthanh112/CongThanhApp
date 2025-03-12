import React from 'react';
import { UseFormReturn } from 'react-hook-form';
import { FormControl, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form';
import { Input } from '@/components/ui/input';
import { ProductSchemaType } from '@/models/schema/productSchema';
import CustomEditor from '@/components/CustomEditor';

type Props = { form: UseFormReturn<ProductSchemaType> };

const TabProductInfo = ({ form }: Props) => {
  return (
    <div>
      <FormField
        control={form.control}
        name="name"
        render={({ field }) => (
          <FormItem className="h-24 space-y-0">
            <FormLabel>Name</FormLabel>
            <FormControl>
              <Input placeholder="example@email.com" type="text" {...field} />
            </FormControl>
            <FormMessage />
          </FormItem>
        )}
      />

      <FormField
        control={form.control}
        name="slug"
        render={({ field }) => (
          <FormItem className="h-24 space-y-0">
            <FormLabel>Slug</FormLabel>
            <FormControl>
              <Input placeholder="example@email.com" {...field} />
            </FormControl>
            <FormMessage />
          </FormItem>
        )}
      />

      <FormField
        control={form.control}
        name="description"
        render={({ field }) => (
          <FormItem className="h-24 space-y-0">
            <FormLabel>Description</FormLabel>
            <FormControl {...field}>
              <CustomEditor />
            </FormControl>
            <FormMessage />
          </FormItem>
        )}
      />
    </div>
  );
};

export default TabProductInfo;

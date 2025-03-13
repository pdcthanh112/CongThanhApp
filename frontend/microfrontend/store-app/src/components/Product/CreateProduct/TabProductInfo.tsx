import React from 'react';
import { UseFormReturn } from 'react-hook-form';
import { FormControl, FormField, FormItem, FormLabel, FormMessage, Input } from '@/components/ui/';
import { ProductSchemaType } from '@/models/schema/productSchema';
import CustomEditor from '@/components/CustomEditor';
import { Autocomplete, TextField } from '@mui/material';

type Props = { form: UseFormReturn<ProductSchemaType> };

const TabProductInfo = ({ form }: Props) => {
  const category = [
    { id: 'a', name: 'AAAAAAAAAAAAAAAAAAAAAA' },
    { id: 'b', name: 'BBBBBBBBBBBBBBBBBBBBBB' },
    { id: 'c', name: 'CCCCCCCCCCCCCCCCCCCCCC' },
    { id: 'd', name: 'DDDDDDDDDDDDDDDDDDDDDD' },
    { id: 'e', name: 'EEEEEEEEEEEEEEEEEEEEEE' },
    { id: 'f', name: 'FFFFFFFFFFFFFFFFFFFFFF' },
  ];

  return (
    <div>
      <FormField
        control={form.control}
        name="name"
        render={({ field }) => (
          <FormItem className=" mb-3 gap-0.5 space-y-0">
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
          <FormItem className=" mb-3 gap-0.5 space-y-0">
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
        name="category"
        render={({ field }) => (
          <FormItem className=" mb-3 gap-0.5 space-y-0">
            <FormLabel>Category</FormLabel>
            <FormControl>
              <Autocomplete
                multiple
                size="small"
                options={category}
                renderInput={(params) => <TextField {...params} label="" />}
                getOptionLabel={(item) => item.name}
                value={category.filter((cat) => field.value?.includes(cat.id))}
                onChange={(_, value) => {
                  const selectedIds = value.map((item) => item.id);
                  field.onChange(selectedIds);
                }}
              />
            </FormControl>
            <FormMessage />
          </FormItem>
        )}
      />

      <FormField
        control={form.control}
        name="description"
        render={({ field }) => (
          <FormItem className=" mb-3 gap-0.5 space-y-0">
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

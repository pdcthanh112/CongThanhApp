import React from 'react';
import { UseFormReturn } from 'react-hook-form';
import { Checkbox, FormControl, FormField, FormItem, FormLabel, FormMessage, Input, Label } from '@/components/ui/';
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
          <FormItem className="gap-0.5 h-20 mb-3">
            <FormLabel style={{ color: 'inherit' }}>Name</FormLabel>
            <FormControl>
              <Input {...field} />
            </FormControl>
            <FormMessage />
          </FormItem>
        )}
      />

      <FormField
        control={form.control}
        name="slug"
        render={({ field }) => (
          <FormItem className="gap-0.5 h-20 mb-3">
            <FormLabel style={{ color: 'inherit' }}>Slug</FormLabel>
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
          <FormItem className="gap-0.5 h-20 mb-3">
            <FormLabel style={{ color: 'inherit' }}>Category</FormLabel>
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
          <FormItem className="gap-0 h-80 mb-3">
            <FormLabel style={{ color: 'inherit' }}>Description</FormLabel>
            <FormControl {...field}>
              <CustomEditor />
            </FormControl>
            <FormMessage />
          </FormItem>
        )}
      />

      <div className="items-top flex space-x-2">
        <Checkbox id="is_featured" />
        <div className="grid gap-1.5 leading-none">
          <Label
            htmlFor="is_featured"
            className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
          >
            Is Featured
          </Label>
        </div>
      </div>
    </div>
  );
};

export default TabProductInfo;

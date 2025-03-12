import React from 'react';
import { useFieldArray, UseFormReturn } from 'react-hook-form';
import { ProductSchemaType } from '@/models/schema/productSchema';
import { Button, FormControl, FormField, FormItem, FormLabel, FormMessage, Input, ScrollArea } from '@/components/ui';
import { Trash } from 'lucide-react';
import { MultiImageUpload } from '@/components/MultiImageUpload';
import { Card } from '@mui/material';

type Props = { form: UseFormReturn<ProductSchemaType> };

const TabProductVariant = ({ form }: Props) => {
  const { fields, append, remove } = useFieldArray({
    control: form.control,
    name: 'variant',
  });

  const images = form.watch('variant.1.image') || [];

  return (
    <React.Fragment>
      {fields.map((field, index) => (
        <div key={field.id} className="flex justify-between w-4/5 mx-auto">
          <Card className="w-11/12   mb-10">
            <ScrollArea className="h-96 p-6">
              <FormField
                control={form.control}
                name={`variant.${index}.sku`}
                render={() => (
                  <FormItem className="col-span-6 mb-3 space-y-0 gap-0.5">
                    <FormLabel>SKU</FormLabel>
                    <FormControl>
                      <Input {...form.register(`variant.${index}.sku`)} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <FormField
                control={form.control}
                name={`variant.${index}.gtin`}
                render={() => (
                  <FormItem className="space-y-0 col-span-6 mb-3 gap-0.5">
                    <FormLabel>Gtin</FormLabel>
                    <FormControl>
                      <Input placeholder="Enter value" className="" {...form.register(`variant.${index}.gtin`)} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <FormField
                control={form.control}
                name={`variant.${index}.price`}
                render={() => (
                  <FormItem className="space-y-0 col-span-6 mb-3 gap-0.5">
                    <FormLabel>Price</FormLabel>
                    <FormControl>
                      <Input placeholder="Enter value" className="" {...form.register(`variant.${index}.price`)} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <FormField
                control={form.control}
                name={`variant.${index}.image`}
                render={({ field }) => (
                  <FormItem className="">
                    <FormLabel>Image</FormLabel>
                    <FormControl {...field} className="w-full border border-gray-400 p-3 rounded">
                      <MultiImageUpload
                        value={images}
                        onChange={(files) => form.setValue(`variant.${index}.image`, files)}
                        maxImages={5}
                      />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
            </ScrollArea>
          </Card>
          <div className="relative">
            <Trash
              className="h-5 w-5 col-span-1 hover:cursor-pointer my-auto absolute top-0 right-0"
              onClick={() => remove(index)}
            />
          </div>
        </div>
      ))}

      <div className=" col-span-12 flex justify-end mt-2">
        <Button
          type="button"
          variant="outline"
          className="hover:cursor-pointer"
          onClick={() => append({ sku: '', gtin: '', price: 0, image: [] })}
        >
          + Add
        </Button>
      </div>
    </React.Fragment>
  );
};

export default TabProductVariant;

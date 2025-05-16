import React from 'react';
import { useFieldArray, UseFormReturn } from 'react-hook-form';
import { ProductSchemaType } from '@/models/schema/productSchema';
import {
  Input,
  Button,
  Select,
  SelectTrigger,
  SelectValue,
  SelectContent,
  SelectItem,
  FormField,
  FormItem,
  FormControl,
  FormMessage,
} from '@/components/ui/';
import { Trash } from 'lucide-react';

type Props = { form: UseFormReturn<ProductSchemaType> };

const TabProductAttribute = ({ form }: Props) => {
  const attribute = [
    { id: 1, name: 'Color' },
    { id: 2, name: 'Material' },
    { id: 3, name: 'Author' },
    { id: 4, name: 'Publisher' },
    { id: 5, name: 'Aesthetics' },
    { id: 6, name: 'Country' },
  ];

  const { fields, append, remove } = useFieldArray({
    control: form.control,
    name: 'attribute',
  });

  return (
    <React.Fragment>
      <div className="grid grid-cols-12 gap-3 w-2/3 mx-auto mb-4">
        {fields.map((field, index) => (
          <React.Fragment key={field.id}>
            <FormField
              control={form.control}
              name={`attribute.${index}.attributeId`}
              render={({ field }) => (
                <FormItem className="col-span-5 w-full">
                  <FormControl>
                    <Select
                      value={form.watch(`attribute.${index}.attributeId`)}
                      onValueChange={(value) => form.setValue(`attribute.${index}.attributeId`, value)}
                    >
                      <SelectTrigger className="w-full">
                        <SelectValue placeholder="Select attribute" />
                      </SelectTrigger>
                      <SelectContent {...field}>
                        {attribute.map((item) => (
                          <SelectItem key={item.id} value={item.id.toString()}>
                            {item.name}
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                  </FormControl>
                </FormItem>
              )}
            />

            <FormField
              control={form.control}
              name={`attribute.${index}.value`}
              render={() => (
                <FormItem className="space-y-0 col-span-6">
                  <FormControl>
                    <Input placeholder="Enter value" className="" {...form.register(`attribute.${index}.value`)} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />

            <Trash className="h-5 w-5 col-span-1 hover:cursor-pointer my-auto" onClick={() => remove(index)} />
          </React.Fragment>
        ))}

        <div className=" col-span-12 flex justify-end mt-2">
          <Button
            type="button"
            variant="outline"
            className="hover:cursor-pointer"
            onClick={() => append({ attributeId: '', value: '' })}
          >
            + Add
          </Button>
        </div>
      </div>
    </React.Fragment>
  );
};

export default TabProductAttribute;

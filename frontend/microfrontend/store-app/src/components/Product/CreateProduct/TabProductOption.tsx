import React, { useState } from 'react';
import { useFieldArray, UseFormReturn } from 'react-hook-form';
import { ProductSchemaType } from '@/models/schema/productSchema';
import { Autocomplete, TextField } from '@mui/material';
import { Button, FormControl, FormField, FormItem, FormMessage, Input } from '@/components/ui';
import DragDropList from '@/components/DragDropList';

type Props = { form: UseFormReturn<ProductSchemaType> };

const TabProductOption = ({ form }: Props) => {
  const option = [
    { id: 1, name: 'Color' },
    { id: 2, name: 'Material' },
    { id: 3, name: 'Author' },
    { id: 4, name: 'Publisher' },
    { id: 5, name: 'Aesthetics' },
    { id: 6, name: 'Country' },
  ];

  const [newOption, setNewOption] = useState<string | null>('');
  const [errorMessage, setErrorMessage] = useState('');

  const { fields, append, remove } = useFieldArray({
    control: form.control,
    name: 'option',
  });

  const [items, setItems] = useState([
    'Write a cool JS library',
    'Make it generic enough',
    'Write README',
    'Create some examples',
    'Spam in Twitter and IRC to promote it (note that this element is taller than the others)',
    '???',
    'PROFIT',
  ]);

  const handleAddOption = () => {
    if (newOption) {
      setErrorMessage('');
      append({ name: newOption, displayOrder: 1, value: [] });
    } else {
      setErrorMessage('Please select option');
    }
  };

  return (
    <React.Fragment>
      <div className="flex">
        <Autocomplete
          options={option}
          renderInput={(params) => <TextField {...params} label="" />}
          getOptionLabel={(item) => item.name}
          size="small"
          sx={{ width: 300, marginRight: 2 }}
          onChange={(_, value) => setNewOption(value?.name ?? '')}
        />
        <Button
          type="button"
          variant="outline"
          className="cursor-pointer rounded h-10"
          onClick={() => handleAddOption()}
        >
          + Add
        </Button>
        {errorMessage && <span>{errorMessage}</span>}
      </div>
      {fields.map((field, index) => (
        <React.Fragment key={field.id}>
          <FormField
            control={form.control}
            name={`option.${index}.value`}
            render={() => (
              <FormItem className="space-y-0 col-span-6">
                <FormControl>
                  <div className="bg-blue-50 mt-3 grid grid-cols-12 p-4 gap-x-4">
                    <div className="col-span-12 flex justify-end">
                      <span className="text-red-500 hover:cursor-pointer hover:underline" onClick={() => remove(index)}>
                        Remove
                      </span>
                    </div>
                    <div className="col-span-3">
                      Name:
                      <Input value={'Size'} />
                    </div>
                    <div className="col-span-9">
                      Value:
                      <div>
                        <DragDropList items={items} onReorder={setItems} />
                      </div>
                    </div>
                  </div>
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
        </React.Fragment>
      ))}
    </React.Fragment>
  );
};

export default TabProductOption;

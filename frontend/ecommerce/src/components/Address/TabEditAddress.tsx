'use client'
import React from 'react';
import { useForm } from 'react-hook-form';
import { Button, Checkbox } from 'antd';
import { Address } from '@/models/types';
import { Autocomplete, TextField } from '@mui/material';
import countryData from '../../../public/data/country.json';
import { useTranslations } from 'next-intl';
import { UpdateAddressForm } from '@/models/form';

type InputComponentProps = {
  title: string;
  children: React.ReactNode;
  error?: any;
  className?: string;
};

const InputComponent: React.FC<InputComponentProps> = (element) => {
  return (
    <div className={`mb-3 h-20 ${element.className}`}>
      <h5 className="font-medium">{element.title}</h5>
      <div className={`${element.error && 'bg-red-100'}`}>{element.children}</div>
      <span className="text-red-500">{element.error}</span>
    </div>
  );
};

type PropsType = {
  address: Address;
  onBack: () => void;
  handleUpdate: (data: UpdateAddressForm) => void
};

const TabEditAddress = ({ address, onBack, handleUpdate }: PropsType) => {

  const t = useTranslations('common');

  const { register, handleSubmit, formState, setValue } = useForm<UpdateAddressForm>();

  return (
    <React.Fragment>
      <h3>Update address</h3>
      <div>
        <form onSubmit={handleSubmit(handleUpdate)}>
          <input type="hidden" {...register('id', {})} defaultValue={address.id} />
          {/* <input type="hidden" {...register('customer', {})} defaultValue={currentUser.userInfo.accountId} /> */}
          <div className="grid grid-cols-12 gap-4">
            <InputComponent title="Phone" className="col-span-7" error={formState.errors.phone?.message}>
              <InputField>
                <input
                  type="text"
                  {...register('phone', {
                    required: 'Phone is require',
                  })}
                  defaultValue={address?.phone}
                  placeholder="Enter your phone number"
                  className={`focus:outline-none ml-3 w-[100%] ${formState.errors.phone && 'bg-red-100'}`}
                />
              </InputField>
            </InputComponent>

            <InputComponent title="Zip Code/Postal" error={formState.errors.postalCode?.message} className="col-span-5">
              <InputField>
                <input
                  type="text"
                  {...register('postalCode', {
                    required: 'Address is require',
                  })}
                  defaultValue={address?.postalCode}
                  placeholder="Enter zip code"
                  className={`focus:outline-none ml-3 w-[100%] ${formState.errors.postalCode && 'bg-red-100'}`}
                />
              </InputField>
            </InputComponent>
          </div>

          <InputComponent title="Country/Region" className="col-span-3" error={formState.errors.country?.message}>
            <Autocomplete
              options={countryData}
              getOptionLabel={(option) => option.country}
              size={'small'}
              // defaultValue={address?.country}
              renderInput={(params) => <TextField {...params} label="" />}
              onInputChange={(event, value) => {
                setValue('country', value);
              }}
            />
          </InputComponent>
          <div className="grid grid-cols-12 gap-2">
            <InputComponent title="City/State" className="col-span-4" error={formState.errors.addressLine1?.message}>
              <Autocomplete
                options={['Ho Chi Minh']}
                size={'small'}
                defaultValue={address?.addressLine1}
                renderInput={(params) => <TextField {...params} label="" />}
                onInputChange={(event, value) => {
                  setValue('addressLine1', value);
                }}
              />
            </InputComponent>
            <InputComponent title="District" className="col-span-4" error={formState.errors.addressLine2?.message}>
              <Autocomplete
                options={['Ho Chi Minh']}
                size={'small'}
                defaultValue={address?.addressLine2}
                renderInput={(params) => <TextField {...params} label="" />}
                onInputChange={(event, value) => {
                  setValue('addressLine2', value);
                }}
              />
            </InputComponent>
            <InputComponent title="Ward" className="col-span-4" error={formState.errors.addressLine3?.message}>
              <Autocomplete
                options={['Ho Chi Minh']}
                size={'small'}
                defaultValue={address?.addressLine3}
                renderInput={(params) => <TextField {...params} label="" />}
                onInputChange={(event, value) => {
                  setValue('addressLine3', value);
                }}
              />
            </InputComponent>
          </div>

          <InputComponent title="Address" error={formState.errors.street?.message}>
            <InputField>
              <input
                type="text"
                {...register('street', {
                  required: 'Address is require',
                })}
                defaultValue={address?.street}
                placeholder="Enter your address"
                className={`focus:outline-none ml-3 w-[100%] ${formState.errors.street && 'bg-red-100'}`}
              />
            </InputField>
          </InputComponent>

          <Checkbox>Set as default</Checkbox>

          <div className="flex justify-end">
            <Button type="default" style={{ marginRight: '8px' }} danger onClick={() => onBack()}>
              Cancel
            </Button>
            <Button type="primary" danger htmlType="submit">
              Save
            </Button>
          </div>
        </form>
      </div>
    </React.Fragment>
  );
};

export default TabEditAddress;

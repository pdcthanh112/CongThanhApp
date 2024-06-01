import React from 'react';
import { Autocomplete, TextField } from '@mui/material';
import { Button, Checkbox } from 'antd';
import { SubmitHandler, useForm } from 'react-hook-form';
import styled from 'styled-components';
import { useAppSelector } from '@redux/store';
import countryData from '../../../public/data/country.json';
import provinceData from '../../../public/data/province.json';
import { CreateAddressForm } from '@models/form';

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

const InputField = styled.div`
  border: 1px solid #b6b6b6;
  padding: 0.45rem 1.8rem 0.45rem 0.6rem;
  border-radius: 4px;
  position: relative;
`;

type PropsType = {
  handleCreate: (data: CreateAddressForm) => void
  onBack: () => void;
};

const TabCreateAddress = ({ handleCreate, onBack }: PropsType) => {
  const currentUser = useAppSelector((state) => state.auth.currentUser);

  const { register, handleSubmit, formState, setValue } = useForm<CreateAddressForm>();

  // const onSubmit: SubmitHandler<CreateAddressForm> = (data) => {
  //   createAddress(data, {
  //     onSuccess() {
  //       toast.success(t('add_successfully'));
  //     },
  //     onError() {
  //       toast.error(t('add_failed'));
  //     },
  //   });
  // };

  return (
    <React.Fragment>
      <h3>create address</h3>
      <div>
        <form onSubmit={handleSubmit(handleCreate)}>
          <input type="hidden" {...register('customer', {})} defaultValue={currentUser.userInfo.accountId} />
          <div className="grid grid-cols-12 gap-4">
            <InputComponent title="Phone" className="col-span-7" error={formState.errors.phone?.message}>
              <InputField>
                <input
                  type="text"
                  {...register('phone', {
                    required: 'Phone is require',
                  })}
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
                  placeholder="Enter zip code"
                  className={`focus:outline-none ml-3 w-[100%] ${formState.errors.postalCode && 'bg-red-100'}`}
                />
              </InputField>
            </InputComponent>
          </div>

          <InputComponent title="Country/Region" className="col-span-3" error={formState.errors.country?.message}>
            <Autocomplete
              options={countryData}
              getOptionLabel={(option) => option.name}
              size={'small'}
              renderInput={(params) => <TextField {...params} label="" />}
              onInputChange={(event, value) => {
                setValue('country', value);
              }}
            />
          </InputComponent>
          <div className="grid grid-cols-12 gap-2">
            <InputComponent title="City/State" className="col-span-4" error={formState.errors.addressLine1?.message}>
              <Autocomplete
                options={provinceData}
                getOptionLabel={(option) => option.name}
                size={'small'}
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
                placeholder="Enter your address"
                className={`focus:outline-none ml-3 w-[100%] ${formState.errors.street && 'bg-red-100'}`}
              />
            </InputField>
          </InputComponent>

          <Checkbox onChange={(event) => setValue('isDefault', event.target.checked)}>Set as default</Checkbox>

          <div className="flex justify-end">
            <Button type="default" style={{ marginRight: '8px' }} danger onClick={() => onBack()}>
              Back
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

export default TabCreateAddress;

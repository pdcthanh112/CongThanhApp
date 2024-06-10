import React, { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { getAddressByCustomer } from '@api/addressApi';
import { useAppSelector } from '@redux/store';
import ManagementLayout from '@layout/ManagementLayout';
import { serverSideTranslations } from 'next-i18next/serverSideTranslations';
import { Address, Customer } from '@models/type';
import { Button } from '@components/UI';
import { PlusIcon } from '@assets/icons';
import { Icon } from '@mui/material';
import TabCreateAddress from '@components/Address/TabCreateAddress';
import { Modal, Popconfirm } from 'antd';
import { useCreateAddress, useDeleteAddress, useUpdateAddress } from '@hooks/address/addressHook';
import { toast } from 'react-toastify';
import AddressSkeleton from './AddressSkeleton';
import TabEditAddress from '@components/Address/TabEditAddress';
import { CreateAddressForm, UpdateAddressForm } from '@models/form';
import { SubmitHandler, useForm } from 'react-hook-form';
import { useTranslations } from 'next-intl';

const AddressPage = (): React.ReactElement => {
  const currentUser: Customer = useAppSelector((state) => state.auth.currentUser);
  const [isCreate, setIsCreate] = useState<boolean>(false);
  const [isUpdate, setIsUpdate] = useState<Address | null>();

  const { mutate: createAddress } = useCreateAddress();
  const { mutate: updateAddress } = useUpdateAddress();
  const { mutate: deleteAddress } = useDeleteAddress();

  const t = useTranslations('common');

  const { data: listAddress, isLoading } = useQuery({
    queryKey: ['address'],
    queryFn: async () => await getAddressByCustomer(currentUser.userInfo.accountId).then((response) => response.data),
  });

  // const { register, handleSubmit, formState } = useForm<UpdateAddressForm>();
  const onCreate: SubmitHandler<CreateAddressForm> = (data) => {
    createAddress(data, {
      onSuccess() {
        setIsCreate(false);
        toast.success(t('create_successfully'));
      },
      onError() {
        toast.error(t('create_failed'));
      },
    });
  };

  const onUpdate: SubmitHandler<UpdateAddressForm> = (data) => {
    updateAddress(data, {
      onSuccess() {
        setIsUpdate(null);
        toast.success(t('change_successfully'));
      },
      onError() {
        toast.error(t('change_failed'));
      },
    });
  };

  const handleDeleteAddress = (addressId: number) => {
    if (currentUser) {
      deleteAddress(addressId, {
        onSuccess() {
          toast.success('success');
        },
        onError(error) {
          toast.error('error');
          console.log(error);
        },
      });
    }
  };

  return (
    <div>
      <h3 className="text-xl">Your address</h3>
      <div className="flex justify-end">
        <Button onClick={() => setIsCreate(true)}>
          <Icon component={PlusIcon} fontSize="small" className="mr-1" />
          <span>Add new address</span>
        </Button>
        {isCreate && (
          <Modal open={isCreate} onCancel={() => setIsCreate(false)} footer={false}>
            <TabCreateAddress
              handleCreate={onCreate}
              onBack={function (): void {
                throw new Error('Function not implemented.');
              }}
            />
          </Modal>
        )}
      </div>
      {isLoading ? (
        <>
          {Array(4).map((_, id) => (
            <AddressSkeleton key={id} />
          ))}
        </>
      ) : (
        <React.Fragment>
          {listAddress.map((item: Address) => (
            <div key={item.id} className="my-2 px-5 py-2 border-t-2 grid grid-cols-12">
              <div className="col-span-10">
                <span className="mr-1 text-lg">{currentUser.userInfo.name}</span>|<span className="ml-2">{item.phone}</span>
                <div className="flex justify-between">
                  <p className="">
                    {item.street}, {item.addressLine3}, {item.addressLine2}, {item.addressLine1}, {item.country}
                  </p>
                </div>
                {item.isDefault && <span className="bg-yellow-300 px-2 py-1 rounded-md">Default</span>}
              </div>
              <div className="col-span-2 text-end">
                <Button className={`text-sm ${item.isDefault && 'invisible'}`}>Mark as default</Button>
                <div>
                  <span title="Edit" className="text-blue-400 hover:cursor-pointer hover:underline mr-2" onClick={() => setIsUpdate(item)}>
                    Edit
                  </span>
                  <Popconfirm title="Delete address" description="Are you sure to delete this address?" onConfirm={() => handleDeleteAddress(item.id)} okText="Yes" cancelText="No">
                    <span title="Delete" className="text-blue-400 hover:cursor-pointer hover:underline">
                      Delete
                    </span>
                  </Popconfirm>
                </div>
              </div>
            </div>
          ))}
          {isUpdate && (
            <Modal open={!!isUpdate} footer={false}>
              <TabEditAddress
                address={isUpdate}
                handleUpdate={onUpdate}
                onBack={() => setIsUpdate(null)}
              />
            </Modal>
          )}
        </React.Fragment>
      )}
    </div>
  );
};

AddressPage.getLayout = function getLayout(page: React.ReactNode) {
  return <ManagementLayout>{page}</ManagementLayout>;
};

export default AddressPage;

export async function getServerSideProps(context: any) {
  return {
    props: {
      ...(await serverSideTranslations(context.locale, ['common'])),
    },
  };
}

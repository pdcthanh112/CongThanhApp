'use client';
import React, { useState } from 'react';
import { DatePicker, Modal, Radio } from 'antd';
import { SubmitHandler, useForm } from 'react-hook-form';
import { EditProfileForm } from '@/models/form';
import Image from 'next/image';
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage, Input } from '@/components/ui';
import { Autocomplete, Icon, TextField } from '@mui/material';
import { Edit, Email } from '@mui/icons-material';
import { useTranslations } from 'next-intl';

export default function EditProfile({ userInfo }: { userInfo: any }) {
  const [isOpenModalEdit, setIsOpenModalEdit] = useState<boolean>(false);
  const [isChangePhone, setIsChangePhone] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const t = useTranslations();

  const form = useForm<EditProfileForm>();
  const onSubmit: SubmitHandler<EditProfileForm> = (data) => {
    // dispatch(login(data)).then((res) => {
    //   if (res.payload.status === 'SUCCESS') {
    //     navigate.push('/home');
    //   }
    // });
    console.log('RRRRRRRRRRRRRRRRRRRR', data);
  };

  const countryData = [
    { label: 'The Shawshank Redemption', year: 1994 },
    { label: 'The Godfather', year: 1972 },
    { label: 'The Godfather: Part II', year: 1974 },
    { label: 'The Dark Knight', year: 2008 },
    { label: '12 Angry Men', year: 1957 },
  ];

  return (
    <React.Fragment>
      <Icon
        titleAccess="Edit"
        component={Edit}
        onClick={() => setIsOpenModalEdit(true)}
        className="opacity-80 hover:opacity-100 hover:cursor-pointer"
      />

      <Modal
        title="Edit profile"
        open={true}
        // open={isOpen}
        onOk={() => setIsOpenModalEdit(false)}
        okText="Save"
        onCancel={() => setIsOpenModalEdit(false)}
        width={800}
      >
        <Form {...form}>
          <form onSubmit={form.handleSubmit(onSubmit)}>
            <div className="grid grid-cols-12 gap-2">
              <div className="col-span-8">
                <FormField
                  name="name"
                  control={form.control}
                  rules={{ required: true }}
                  render={({ field }) => (
                    <FormItem className="h-24 space-y-0">
                      <FormLabel style={{ color: 'inherit' }}>
                        {t('auth.email')}
                        <span className="text-red-500">*</span>
                      </FormLabel>
                      <FormControl>
                        <div className="flex items-center border border-gray-500 rounded">
                          <Icon component={Email} className="ml-2" />
                          <Input
                            type="email"
                            defaultValue={userInfo.email}
                            disabled
                            {...field}
                            className="border-none"
                          />
                        </div>
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />

                <FormField
                  name="name"
                  control={form.control}
                  rules={{ required: true }}
                  render={({ field }) => (
                    <FormItem className="h-24 space-y-0">
                      <FormLabel style={{ color: 'inherit' }}>
                        {t('auth.email')}
                        <span className="text-red-500">*</span>
                      </FormLabel>
                      <FormControl>
                        <div className="flex items-center border border-gray-500 rounded">
                          <Icon component={Email} className="ml-2" />
                          <Input
                            type="email"
                            placeholder={t('placeholder.input_field', { field: t('auth.email') })}
                            {...field}
                            className="border-none"
                          />
                        </div>
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />

                <div className="flex justify-between">
                  <FormField
                    name="phone"
                    control={form.control}
                    rules={{ required: true }}
                    render={({ field }) => (
                      <FormItem className="h-24 space-y-0">
                        <FormLabel style={{ color: 'inherit' }}>
                          {t('auth.phone')}
                          <span className="text-red-500">*</span>
                        </FormLabel>
                        <FormControl>
                          <div className="flex items-center border border-gray-500 rounded">
                            <Icon component={Email} className="ml-2" />
                            <Input
                              type="tel"
                              placeholder={t('placeholder.input_field', { field: t('auth.phone') })}
                              {...field}
                              className="border-none"
                            />
                          </div>
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <FormField
                    name="gender"
                    control={form.control}
                    rules={{ required: true }}
                    render={({ field }) => (
                      <FormItem className="h-24 space-y-0">
                        <FormLabel style={{ color: 'inherit' }}>
                          {t('auth.gender')}
                          <span className="text-red-500">*</span>
                        </FormLabel>
                        <FormControl>
                          <div className="flex items-center border border-gray-500 rounded">
                            <Icon component={Email} className="ml-2" />
                            <Input
                              type="email"
                              placeholder={t('placeholder.input_field', { field: t('auth.email') })}
                              {...field}
                              className="border-none"
                            />
                          </div>
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                </div>

                <div className="flex justify-between">
                  <FormField
                    name="dob"
                    control={form.control}
                    rules={{ required: true }}
                    render={({ field }) => (
                      <FormItem className="h-24 space-y-0">
                        <FormLabel style={{ color: 'inherit' }}>
                          {t('auth.phone')}
                          <span className="text-red-500">*</span>
                        </FormLabel>
                        <FormControl>
                          <div className="flex items-center border border-gray-500 rounded">
                            <Icon component={Email} className="ml-2" />
                            <DatePicker
                              size="large"
                              className="border-none outline-none focus:outline-none"
                              {...field}
                            />
                          </div>
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <FormField
                    name="country"
                    control={form.control}
                    rules={{ required: true }}
                    render={({ field }) => (
                      <FormItem className="h-24 space-y-0">
                        <FormLabel style={{ color: 'inherit' }}>
                          {t('auth.gender')}
                          <span className="text-red-500">*</span>
                        </FormLabel>
                        <FormControl>
                          <div className="flex items-center border border-gray-500 rounded">
                            <Icon component={Email} className="ml-2" />
                            <Autocomplete
                              size="small"
                              disablePortal
                              className=""
                              options={countryData}
                              sx={{ width: '100%' }}
                              renderInput={(params) => <TextField {...params} className="" />}
                            />
                          </div>
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                </div>
              </div>
              <div className="col-span-4 flex justify-center items-center">
                <Image src={userInfo.image} alt={'User image'} width={100} height={100} />
              </div>
            </div>
            <div className="italic">
              (<span className="text-red-400 font-bold">*</span>) field is required
            </div>
          </form>
        </Form>
      </Modal>
    </React.Fragment>
  );
}

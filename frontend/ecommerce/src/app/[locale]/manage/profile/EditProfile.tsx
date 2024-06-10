import React, { useState } from 'react';
import { Modal, Radio } from 'antd';
import styled from 'styled-components';
import { useTranslation } from 'react-i18next';
import { SubmitHandler, useForm } from 'react-hook-form';
import { EditProfileForm } from '@models/form';
import { Customer } from '@models/type';
import { useAppSelector } from '@redux/store';
import Image from 'next/image';
import { Button } from '@components/UI';

type PropsType = {
  isOpen: boolean;
  handleOpen: (value: boolean) => void;
};

type InputComponentProps = {
  title: string;
  children: React.ReactNode;
  error?: any;
  className?: string;
};

const InputComponent: React.FC<InputComponentProps> = (element) => {
  return (
    <div className={`mb-3 h-20 ${element.className}`}>
      <h5 className="font-medium">
        {element.title}
        <span className="text-red-400 font-bold ml-0.5">*</span>
      </h5>
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

const EditProfile = ({ isOpen, handleOpen }: PropsType) => {
  const currentUser: Customer = useAppSelector((state) => state.auth.currentUser);
  const [isChangePhone, setIsChangePhone] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const t = useTranslations('common');

  const { register, setValue, watch, handleSubmit, formState, getValues } = useForm<EditProfileForm>();
  const onSubmit: SubmitHandler<EditProfileForm> = (data) => {
    // dispatch(login(data)).then((res) => {
    //   if (res.payload.status === 'SUCCESS') {
    //     navigate.push('/home');
    //   }
    // });
    console.log('RRRRRRRRRRRRRRRRRRRR', data);
  };

  return (
    <Modal title="Edit profile" open={isOpen} onOk={() => handleOpen(false)} okText="Save" onCancel={() => handleOpen(false)} width={800}>
      <form onSubmit={handleSubmit(onSubmit)}>
        <div className="grid grid-cols-8 gap-2">
          <div className="col-span-5">
            <InputComponent title={t('name')} className="col-span-4" error={formState.errors.name?.message}>
              <InputField>
                <input
                  type="text"
                  {...register('name', {
                    required: 'Name is require',
                  })}
                  defaultValue={currentUser.userInfo.name}
                  placeholder="Enter your name"
                  className={`focus:outline-none ml-3 w-[100%] ${formState.errors.name && 'bg-red-100'}`}
                />
              </InputField>
            </InputComponent>
            <div className="flex justify-between">
              <InputComponent title={t('gender')} className="col-span-4 pt-2" error={formState.errors.phone?.message}>
                {/* <InputField> */}
                {/* <input
                    type="text"
                    {...register('phone', {
                      required: 'Phone is require',
                    })}
                    placeholder="Enter your phone number"
                    className={`focus:outline-none ml-3 w-[100%] ${formState.errors.phone && 'bg-red-100'}`}
                  /> */}
                <Radio.Group value={currentUser.userInfo.gender}>
                  <Radio value={'MALE'}>Male</Radio>
                  <Radio value={'FEMALE'}>Female</Radio>
                  <Radio value={'OTHER'}>Other</Radio>
                </Radio.Group>
                {/* </InputField> */}
              </InputComponent>

              <InputComponent title={t('dob')} className="col-span-4" error={formState.errors.dob?.message}>
                <InputField>
                  <input
                    type="date"
                    defaultValue={Date()}
                    placeholder="Enter your phone number"
                    className={`focus:outline-none ml-3 w-[100%] ${formState.errors.dob && 'bg-red-100'}`}
                  />
                </InputField>
                <></>
              </InputComponent>
            </div>
            <InputComponent title={t('signup.phone')} className="col-span-4" error={formState.errors.phone?.message}>
              <div className="flex">
                <InputField className='w-80'>
                  <input
                    type="tel"
                    {...register('phone', {
                      required: 'Phone is require',
                    })}
                    disabled={!isChangePhone}
                    defaultValue={currentUser.userInfo.phone}
                    placeholder="Enter your phone number"
                    className={`focus:outline-none ml-3 w-[100%] ${formState.errors.phone && 'bg-red-100'}`}
                  />
                </InputField>
                <Button className='ml-5' onClick={() => setIsChangePhone(true)}>Change</Button>
                <Modal title="Basic Modal" open={isModalOpen} onOk={() => setIsModalOpen(false)} onCancel={() => setIsModalOpen(false)}>
                  <p>Some contents...</p>
                  <p>Some contents...</p>
                  <p>Some contents...</p>
                </Modal>
              </div>
            </InputComponent>
          </div>
          <div className="col-span-3 flex justify-center items-center">
            <Image src={currentUser.userInfo.image} alt={'User image'} width={100} height={100} />
          </div>
        </div>
        <div className='italic'>(<span className="text-red-400 font-bold">*</span>) field is require</div>
      </form>
    </Modal>
  );
};

export default EditProfile;

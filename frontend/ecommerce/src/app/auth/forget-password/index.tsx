import React from 'react';
import { useForm, SubmitHandler } from 'react-hook-form';
import { serverSideTranslations } from 'next-i18next/serverSideTranslations';
import { Button } from '@components/UI';
import { Result } from 'antd';
import { Lock } from '@mui/icons-material';
import { Icon } from '@mui/material';

const ForgetPassword = () => {
  const { register, handleSubmit, formState } = useForm<{ email: string }>();
  const onSubmit: SubmitHandler<{ email: string }> = (data) => {
    console.log('first', data);
  };

  return (
    <Result
      icon={<Icon component={Lock} />}
      title="Reset password"
      subTitle="Please input your email to reset password"
      className="bg-white h-[37rem]"
      extra={
        <>
          <form onSubmit={handleSubmit(onSubmit)} className="flex justify-center">
            <input
              type="text"
              {...register('email', {
                required: 'Email is require',
                pattern: { value: /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/, message: 'Email is invalid' },
              })}
              placeholder="Enter your email"
              className={`px-3 py-1 border-2 border-gray-600 focus:outline-none ml-3 w-[22rem] ${formState.errors.email && 'bg-red-100'}`}
            />
            <Button className="ml-3 px-6">Send</Button>
          </form>
          <>{formState.errors.email && <span className="text-red-500">{formState.errors.email.message}</span>}</>
        </>
      }
    />
  );
};

export default ForgetPassword;

export async function getServerSideProps(context: any) {
  return {
    props: {
      ...(await serverSideTranslations(context.locale, ['common'])),
    },
  };
}

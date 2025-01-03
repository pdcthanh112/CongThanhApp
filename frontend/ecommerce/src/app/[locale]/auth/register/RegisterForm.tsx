'use client';
import React, { useState } from 'react';
import { SubmitHandler, useForm } from 'react-hook-form';
import { RegisterSchema, RegisterSchemaType } from '@/models/schema/authSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form';
import { Button, Input } from '@/components/ui';
import { Icon } from '@mui/material';
import { Visibility, VisibilityOff } from '@mui/icons-material';
import { DatePicker, Divider, Result, Spin, Tooltip } from 'antd';
import { signIn } from 'next-auth/react';
import { AppleIcon, FacebookIcon, GoogleIcon, TwitterIcon } from '@/assets/icons/socialLoginIcon';
import ValidatePassword from '@/components/ValidatePassword/ValidatePassword';
import { CalendarDays, CheckCheck, LockKeyhole, Mail, Phone, User } from 'lucide-react';

export default function RegisterForm() {
  const [showPassword, setShowPassword] = useState<boolean>(false);
  const [showModalValidatePassword, setShowModalValidatePassword] = useState<boolean>(false);

  const formRegister = useForm<RegisterSchemaType>({
    resolver: zodResolver(RegisterSchema),
    defaultValues: {
      email: '',
      name: '',
      password: '',
      confirm: '',
      phone: '',
    },
  });

  const onSubmit: SubmitHandler<RegisterSchemaType> = (data) => {
    console.log('asflafjfksf', data);
  };

  return (
    <React.Fragment>
      <Form {...formRegister}>
        <form onSubmit={formRegister.handleSubmit(onSubmit)} className="grid grid-cols-12">
          <FormField
            control={formRegister.control}
            name="email"
            render={({ field }) => (
              <FormItem className="h-24 col-span-12">
                <FormLabel>
                  Email<span className="text-red-500">*</span>
                </FormLabel>
                <FormControl>
                  <div className="flex items-center border-2 px-3 rounded">
                    <Mail />
                    <Input placeholder="example@email.com" type="email" {...field} className="border-none" />
                  </div>
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={formRegister.control}
            name="name"
            render={({ field }) => (
              <FormItem className="h-24 col-span-12">
                <FormLabel>
                  Name<span className="text-red-500">*</span>
                </FormLabel>
                <FormControl>
                  <div className="flex items-center border-2 px-3 rounded">
                    <User />
                    <Input placeholder="" type="text" {...field} className="border-none" />
                  </div>
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <div className="flex col-span-12 gap-4">
            <FormField
              control={formRegister.control}
              name="password"
              render={({ field }) => (
                <FormItem className="h-24 w-full col-span-6">
                  <FormLabel>
                    Password<span className="text-red-500">*</span>
                  </FormLabel>
                  <FormControl>
                    <Tooltip
                      title={<ValidatePassword password={formRegister.getValues('password')} />}
                      placement="top"
                      color="#fff"
                      overlayStyle={{ maxWidth: '500px' }}
                      fresh
                      open={showModalValidatePassword}
                    >
                      <div className="relative flex items-center border-2 px-3 rounded">
                        <LockKeyhole />
                        <Input
                          placeholder="********"
                          type={showPassword ? 'password' : 'text'}
                          className="border-none"
                          {...field}
                          onFocus={() => setShowModalValidatePassword(true)}
                          onBlur={() => setShowModalValidatePassword(false)}
                        />
                        <Icon
                          fontSize="small"
                          component={showPassword ? VisibilityOff : Visibility}
                          cursor={'pointer'}
                          className="absolute right-3 top-2"
                          onClick={() => setShowPassword(!showPassword)}
                        />
                      </div>
                    </Tooltip>
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={formRegister.control}
              name="confirm"
              render={({ field }) => (
                <FormItem className="h-24 w-full col-span-6">
                  <FormLabel>
                    Confirm<span className="text-red-500">*</span>
                  </FormLabel>
                  <FormControl>
                    <div className="flex relative items-center border-2 px-3 rounded">
                      <LockKeyhole />
                      <Input
                        placeholder="**********"
                        type={showPassword ? 'text' : 'password'}
                        {...field}
                        className="border-none"
                      />
                      {formRegister.getValues('password') === formRegister.watch('confirm') ? (
                        <CheckCheck size={20} className="absolute right-2 top-2 text-green-500" />
                      ) : (
                        <Spin size="small" className="absolute right-2 top-3" />
                      )}
                    </div>
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
          </div>

          <div className="flex col-span-12 gap-4">
            <FormField
              control={formRegister.control}
              name="password"
              render={({ field }) => (
                <FormItem className="h-24 w-full col-span-6">
                  <FormLabel style={{ color: 'inherit' }}>Phone</FormLabel>
                  <FormControl>
                    <div className="relative flex items-center border-2 px-3 rounded">
                      <Phone />
                      <Input type="text" className="border-none" {...field} />
                    </div>
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={formRegister.control}
              name="confirm"
              render={({ field }) => (
                <FormItem className="h-24 w-full col-span-6">
                  <FormLabel>Dob</FormLabel>
                  <FormControl>
                    {/* <div className="flex relative items-center border-2 px-3 rounded">
                      <CalendarDays /> */}
                      {/* <Input type="text" {...field} className="border-none" /> */}
                    <DatePicker size='large' className='focus:outline-none w-full border-2 border-gray-200' />
                    {/* </div> */}
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
          </div>
          <Button type="submit" className="col-span-12 mt-3">
            Đăng ký
          </Button>
        </form>
      </Form>

      <Divider>or</Divider>

      <div className="grid grid-cols-12 gap-3 mt-3">
        <div className="col-span-6">
          <SocialLoginComponent id={'google'} name={'Google'} bgColor={'bg-red-400'} icon={GoogleIcon} />
          <SocialLoginComponent id={'twitter'} name={'Twitter'} bgColor={'bg-blue-300'} icon={TwitterIcon} />
        </div>
        <div className="col-span-6">
          <SocialLoginComponent id={'facebook'} name={'Facebook'} bgColor={'bg-blue-400'} icon={FacebookIcon} />
          <SocialLoginComponent id={'apple'} name={'Apple'} bgColor={'bg-gray-400'} icon={AppleIcon} />
        </div>
      </div>
    </React.Fragment>
  );
}

const SocialLoginComponent = ({
  id,
  name,
  bgColor,
  icon,
}: {
  id: 'google' | 'facebook' | 'twitter' | 'apple';
  name: 'Google' | 'Facebook' | 'Twitter' | 'Apple';
  bgColor: string;
  icon: any;
}) => (
  <div
    className={`${bgColor} flex px-3 py-3 mb-3 hover:cursor-pointer rounded-lg`}
    title={`Login with ${name}`}
    onClick={() =>
      signIn(id)
        .then(() => console.log(`${id} login initiated`))
        .catch((err) => console.error('Sign in error:', err))
    }
  >
    <Icon component={icon} className="h-2" />
    <span className="ml-3 text-white font-medium">Login with {name}</span>
  </div>
);

import React, { useState } from 'react';
import { Controller, SubmitHandler, useForm } from 'react-hook-form';
import { Input } from '@/components/ui/input';
import { FormHelperText, Icon } from '@mui/material';
import { Email, Password, Visibility, VisibilityOff } from '@mui/icons-material';
import Link from 'next/link';
import { signIn } from 'next-auth/react';
import { Button } from '@/components/ui/button';
import { useTranslations } from 'next-intl';
import { LoginSchema, LoginSchemaType } from '@/models/schema/authSchema';
import { PATH } from '@/utils/constants/path';
import { zodResolver } from '@hookform/resolvers/zod';
import { Divider } from 'antd';
import { AppleIcon, FacebookIcon, GoogleIcon, TwitterIcon } from '@/assets/icons/socialLoginIcon';
import { Form } from '../ui';

export default function LoginComponent() {
  const t = useTranslations();

  const [showPassword, setShowPassword] = useState<boolean>(false);

  const formLogin = useForm<LoginSchemaType>({
    defaultValues: {
      email: '',
      password: '',
    },
    resolver: zodResolver(LoginSchema),
  });

  const onSubmit: SubmitHandler<LoginSchemaType> = (data) => {
    console.log('AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA', data);
  };

  return (
    <React.Fragment>
      <Form {...formLogin}>
        <form onSubmit={formLogin.handleSubmit(onSubmit)}>
          <Controller
            name="email"
            control={formLogin.control}
            rules={{ required: true }}
            render={({ field }) => (
              <div className="h-20">
                <div className="flex items-center border border-gray-500 rounded">
                  <Icon component={Email} className="ml-2" />
                  <Input type="email" placeholder="Enter your email" {...field} className="border-none" />
                </div>
                <FormHelperText error>{formLogin.formState.errors.email?.message}</FormHelperText>
              </div>
            )}
          />

          <Controller
            name="password"
            control={formLogin.control}
            rules={{ required: true }}
            render={({ field }) => (
              <div className="h-20">
                <div className="flex items-center border border-gray-500 rounded">
                  <Icon component={Password} className="ml-2" />
                  <Input
                    type={showPassword ? 'text' : 'password'}
                    placeholder="Enter your password"
                    {...field}
                    className="border-none"
                  />
                  <Icon
                    component={showPassword ? Visibility : VisibilityOff}
                    fontSize="small"
                    className="mr-3"
                    onClick={() => setShowPassword(!showPassword)}
                  />
                </div>
                <div className="flex justify-between">
                  <FormHelperText error>{formLogin.formState.errors.password?.message}</FormHelperText>
                  <Link
                    href={PATH.AUTH_PATH_URL.FORGET_PASSWORD}
                    className="hover:underline text-sm"
                    title="Forgot password"
                  >
                    {t('login.forgot_password')}
                  </Link>
                </div>
              </div>
            )}
          />

          {/* {loginError && <div className="input-error p-2 rounded">Tên đăng nhập hoặc mật khẩu không chính xác</div>} */}

          <div className="mt-3">
            <Button className="bg-yellow-400 w-full text-white text-lg font-medium" type="submit">
              Login
            </Button>
            {/* {isLoadingAuth && <ReactLoading className="ml-2" type="spin" color="#FF4444" width={37} />} */}
          </div>
        </form>
      </Form>
      {/* <div className="flex justify-between mt-5">
        {providers.map((item: any) => {
          const data = getAuthLogo(item.id);
          return (
            <div
              key={item.id}
              title={`Login with ${item.name}`}
              className={`flex items-center px-3 py-2 rounded-sm text-white font-medium hover:cursor-pointer bg-[${data.bgColor}]`}
              onClick={() => signIn(item.id)}
            >
              <Image src={data.img} alt={''} width={20} />
              <span className="ml-1 text-sm">{item.name}</span>
            </div>
          );
        })}
      </div> */}
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

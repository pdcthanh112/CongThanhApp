'use client';
import React, { useState } from 'react';
import { zodResolver } from '@hookform/resolvers/zod';
import { SubmitHandler, useForm } from 'react-hook-form';
import { Button } from '@/components/ui/button';
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form';
import { Input } from '@/components/ui/input';
import { Divider, Icon } from '@mui/material';
import { Visibility, VisibilityOff } from '@mui/icons-material';
import { LoginSchema, LoginSchemaType } from '@/models/schema/authSchema';
import Link from 'next/link';
import { ClientSafeProvider, LiteralUnion, useSession } from 'next-auth/react';
import { signIn } from 'next-auth/react';
import { BuiltInProviderType } from 'next-auth/providers/index';
import { AppleIcon, FacebookIcon, GoogleIcon, TwitterIcon } from '@/assets/icons/socialLoginIcon';
import { useTranslations } from 'next-intl';

type PropsType = {
  providers: Record<LiteralUnion<BuiltInProviderType, string>, ClientSafeProvider> | null;
  csrfToken: string;
};

export default function LoginForm({ providers, csrfToken }: PropsType) {
  // const [session, loading] = useSession();
  const [showPassword, setShowPassword] = useState<boolean>(false);

  const t = useTranslations();

  const formLogin = useForm<LoginSchemaType>({
    defaultValues: {
      email: '',
      password: '',
      fcmToken: '',
    },
    resolver: zodResolver(LoginSchema),
  });

  const onSubmit: SubmitHandler<LoginSchemaType> = (data) => {
    console.log('asflafjfksf', data);
  };

  return (
    <React.Fragment>
      <Form {...formLogin}>
        <form onSubmit={formLogin.handleSubmit(onSubmit)}>
          <input name="csrfToken" type="hidden" defaultValue={csrfToken} />
          <FormField
            control={formLogin.control}
            name="email"
            render={({ field }) => (
              <FormItem className="h-24">
                <FormLabel>Email</FormLabel>
                <FormControl>
                  <Input placeholder="example@email.com" type="email" {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={formLogin.control}
            name="password"
            render={({ field }) => (
              <FormItem className="h-24">
                <FormLabel style={{ color: 'inherit' }}>Password</FormLabel>
                <FormControl>
                  <div className="relative">
                    <Input placeholder="********" type={showPassword ? 'password' : 'text'} {...field} />
                    <Icon
                      fontSize="small"
                      component={showPassword ? VisibilityOff : Visibility}
                      cursor={'pointer'}
                      className="absolute right-3 top-2"
                      onClick={() => setShowPassword(!showPassword)}
                    />
                    <Link
                      href={'/forget-password'}
                      className="absolute right-0 hover:underline"
                      title="Forget password"
                    >
                      Forget password
                    </Link>
                  </div>
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <Button type="submit" className="!mt-8 w-full">
            Đăng nhập
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

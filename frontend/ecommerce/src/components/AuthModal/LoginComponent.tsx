import React, { useState } from 'react';
import { Controller, SubmitHandler, useForm } from 'react-hook-form';
import { Input } from '@/components/ui/input';
import { FormHelperText, Icon } from '@mui/material';
import { Email, Password, Visibility, VisibilityOff } from '@mui/icons-material';
import Link from 'next/link';
import { Button } from '@/components/ui/button';
import { Checkbox } from '@/components/ui/checkbox';
import { useTranslations } from 'next-intl';
import { LoginSchema, LoginSchemaType } from '@/models/schema/authSchema';
import { PATH } from '@/utils/constants/path';
import { zodResolver } from '@hookform/resolvers/zod';

const LoginComponent = () => {
  const [showPassword, setShowPassword] = useState<boolean>(false);

  const t = useTranslations();

  const {
    handleSubmit,
    control,
    reset,
    formState: { errors },
  } = useForm<LoginSchemaType>({
    defaultValues: {
      email: '',
      password: '',
      remember: false,
    },
    resolver: zodResolver(LoginSchema),
  });

  const onSubmit: SubmitHandler<LoginSchemaType> = (data) => {
    console.log('AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA', data);
  };

  return (
    <React.Fragment>
      <form onSubmit={handleSubmit(onSubmit)}>
        <Controller
          name="email"
          control={control}
          rules={{ required: true }}
          render={({ field }) => (
            <div className="h-20">
              <div className="flex items-center border border-gray-500 rounded">
                <Icon component={Email} className="ml-2" />
                <Input type="email" {...field} className="border-none" />
              </div>
              <FormHelperText error>{errors.email?.message}</FormHelperText>
            </div>
          )}
        />

        <Controller
          name="password"
          control={control}
          rules={{ required: true }}
          render={({ field }) => (
            <div className="h-20">
              <div className="flex items-center border border-gray-500 rounded">
                <Icon component={Password} className="ml-2" />
                <Input type={showPassword ? 'text' : 'password'} {...field} className="border-none" />
                <Icon
                  component={showPassword ? Visibility : VisibilityOff}
                  fontSize="small"
                  className="mr-3"
                  onClick={() => setShowPassword(!showPassword)}
                />
              </div>
              <div className="flex justify-between">
                <FormHelperText error>{errors.password?.message}</FormHelperText>
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

        <Controller
          name="remember"
          control={control}
          rules={{ required: false }}
          render={({ field }) => (
            <div className="flex items-center space-x-2" {...field}>
              <Checkbox id="terms" />
              <label
                htmlFor="terms"
                className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
              >
                {t('login.remember_me')}
              </label>
            </div>
          )}
        />

        {/* {loginError && <div className="input-error p-2 rounded">Tên đăng nhập hoặc mật khẩu không chính xác</div>} */}

        <div className="mt-4">
          <Button className="bg-yellow-400 w-full text-white text-lg font-medium" type="submit">
            Login
          </Button>
          {/* {isLoadingAuth && <ReactLoading className="ml-2" type="spin" color="#FF4444" width={37} />} */}
        </div>
      </form>
    </React.Fragment>
  );
};

export default LoginComponent;

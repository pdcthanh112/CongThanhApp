import React, { useState } from 'react';
import { Controller, SubmitHandler, useForm } from 'react-hook-form';
import { useTranslations } from 'next-intl';
import { RegisterSchema, RegisterSchemaType } from '@/models/schema/authSchema';
import { FormHelperText, Icon } from '@mui/material';
import { AccountCircle, Email, Password, Visibility, VisibilityOff } from '@mui/icons-material';
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import { zodResolver } from '@hookform/resolvers/zod';

const RegisterComponent = () => {
  const t = useTranslations();

  const [showPassword, setShowPassword] = useState<boolean>(false);

  const {
    handleSubmit,
    control,
    reset,
    formState: { errors },
  } = useForm<RegisterSchemaType>({
    defaultValues: {
      email: '',
      password: '',
      phone: '',
    },
    resolver: zodResolver(RegisterSchema),
  });

  const onSubmit: SubmitHandler<RegisterSchemaType> = (data) => console.log(data);

  return (
    <React.Fragment>
      <form onSubmit={handleSubmit(onSubmit)}>
        <Controller
          name="email"
          control={control}
          rules={{ required: true }}
          render={({ field }) => (
            <div className="h-[4.5rem]">
              <div className="flex items-center border border-gray-500 rounded">
                <Icon component={Email} className="ml-2" />
                <Input type="email" {...field} placeholder='Enter your email' className="border-none" />
              </div>
              <FormHelperText error>{errors.email?.message}</FormHelperText>
            </div>
          )}
        />
        <Controller
          name="name"
          control={control}
          rules={{ required: true }}
          render={({ field }) => (
            <div className="h-[4.5rem]">
              <div className="flex items-center border border-gray-500 rounded">
                <Icon component={AccountCircle} className="ml-2" />
                <Input {...field} placeholder='Enter your fullname' className="border-none" />
              </div>
              <FormHelperText error>{errors.name?.message}</FormHelperText>
            </div>
          )}
        />

        <Controller
          name="password"
          control={control}
          rules={{ required: true }}
          render={({ field }) => (
            <div className="h-[4.5rem]">
              <div className="flex items-center border border-gray-500 rounded">
                <Icon component={Password} className="ml-2" />
                <Input {...field} type={showPassword ? 'text' : 'password'} placeholder='Enter your password' className="border-none" />
                <Icon
                  component={showPassword ? Visibility : VisibilityOff}
                  fontSize="small"
                  className="mr-3"
                  onClick={() => setShowPassword(!showPassword)}
                />
              </div>
              <FormHelperText error>{errors.password?.message}</FormHelperText>
            </div>
          )}
        />

        <Controller
          name="confirm"
          control={control}
          rules={{ required: true }}
          render={({ field }) => (
            <div className="h-[4.5rem]">
              <div className="flex items-center border border-gray-500 rounded">
                <Icon component={Password} className="ml-2" />
                <Input {...field} type={showPassword ? 'text' : 'password'} placeholder='Confirm your password' className="border-none" />
                <Icon
                  component={showPassword ? Visibility : VisibilityOff}
                  fontSize="small"
                  className="mr-3"
                  onClick={() => setShowPassword(!showPassword)}
                />
              </div>
              <FormHelperText error>{errors.confirm?.message}</FormHelperText>
            </div>
          )}
        />

        <Button className="bg-yellow-400 w-full text-white text-lg font-medium">Signup</Button>
        {/* {isLoadingAuth && <ReactLoading className="ml-2" type="spin" color="#FF4444" width={37} />} */}
      </form>
    </React.Fragment>
  );
};

export default RegisterComponent;

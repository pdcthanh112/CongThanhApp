'use client';
import React, { useState } from 'react';
import { SubmitHandler, useForm } from 'react-hook-form';
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form';
import { createChangePasswordSchema, ChangePasswordSchemaType } from '@/models/schema/authSchema';
import { useTranslations } from 'next-intl';
import { zodResolver } from '@hookform/resolvers/zod';
import { Button, Input } from '@/components/ui';
import Link from 'next/link';
import { Icon } from '@mui/material';
import { Visibility, VisibilityOff } from '@mui/icons-material';
import { Tooltip } from 'antd';
import ValidatePassword from '@/components/ValidatePassword/ValidatePassword';

export default function ChangePasswordForm() {
  const [showPassword, setShowPassword] = useState<boolean>(false);
  const [showModalValidatePassword, setShowModalValidatePassword] = useState<boolean>(false);

  const t = useTranslations();

  const ChangePasswordSchema = createChangePasswordSchema(t);

  const form = useForm<ChangePasswordSchemaType>({
    defaultValues: {},
    resolver: zodResolver(ChangePasswordSchema),
  });

  const onSubmit: SubmitHandler<ChangePasswordSchemaType> = (data) => {
    console.log('asflafjfksf', data);
  };
  return (
    <React.Fragment>
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)}>
          <FormField
            control={form.control}
            name="currentPassword"
            render={({ field }) => (
              <FormItem className="h-24">
                <FormLabel>{t('auth.change_password.current_password')}</FormLabel>
                <FormControl>
                  <div className="relative">
                    <Input placeholder="example@email.com" type="text" {...field} />
                    <Link
                      href={'/forget-password'}
                      className="absolute right-0 hover:underline"
                      title="Forget password"
                    >
                      {t('auth.forgot_password')}
                    </Link>
                  </div>
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="newPassword"
            render={({ field }) => (
              <FormItem className="h-24">
                <FormLabel style={{ color: 'inherit' }}>{t('auth.change_password.new_password')}</FormLabel>
                <FormControl>
                  <Tooltip
                    title={<ValidatePassword password={form.getValues('newPassword')} />}
                    placement="top"
                    color="#fff"
                    overlayStyle={{ maxWidth: '500px' }}
                    fresh
                    open={showModalValidatePassword}
                  >
                    <Input placeholder="********" type={showPassword ? 'password' : 'text'} {...field} />
                    <Icon
                      fontSize="small"
                      component={showPassword ? VisibilityOff : Visibility}
                      cursor={'pointer'}
                      className="absolute right-3 top-2"
                      onClick={() => setShowPassword(!showPassword)}
                    />
                  </Tooltip>
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="confirmPassword"
            render={({ field }) => (
              <FormItem className="h-24">
                <FormLabel style={{ color: 'inherit' }}>{t('auth.change_password.confirm_password')}</FormLabel>
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
                  </div>
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <Button type="submit" className="!mt-8 w-full">
            {t('auth.login')}
          </Button>
        </form>
      </Form>
    </React.Fragment>
  );
}

'use client';

import React from 'react';
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form';
import { SubmitHandler, useForm } from 'react-hook-form';
import { createForgetPasswordSchema, ForgetPasswordSchemaType } from '@/models/schema/authSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import { Button, Input } from '@/components/ui/';
import { useTranslations } from 'next-intl';

export default function ForgetPasswordForm() {
  const t = useTranslations();
  const ForgetPasswordSchema = createForgetPasswordSchema(t);

  const forgetPasswordForm = useForm<ForgetPasswordSchemaType>({
    defaultValues: {
      email: '',
    },
    resolver: zodResolver(ForgetPasswordSchema),
  });

  const onSubmit: SubmitHandler<ForgetPasswordSchemaType> = (data) => {
    console.log('asflafjfksf', data);
  };

  return (
    <Form {...forgetPasswordForm}>
      <form onSubmit={forgetPasswordForm.handleSubmit(onSubmit)} noValidate>
        <FormField
          control={forgetPasswordForm.control}
          name="email"
          render={({ field }) => (
            <FormItem className="h-24">
              <FormLabel>Email</FormLabel>
              <FormControl>
                <Input placeholder="example@email.com" type="email" {...field} className="focus:outline-hidden" />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <Button type="submit" className="mt-8! w-full">
          Send
        </Button>
      </form>
    </Form>
  );
}

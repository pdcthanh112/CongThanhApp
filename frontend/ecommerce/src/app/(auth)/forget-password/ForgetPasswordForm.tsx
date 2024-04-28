'use client'

import React from "react";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form";
import { SubmitHandler, useForm } from "react-hook-form";
import { forgetPasswordSchema, forgetPasswordType } from "@/models/schema/authSchema";
import { zodResolver } from "@hookform/resolvers/zod";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";

const ForgetPasswordForm = () => {
  const forgetPasswordLogin = useForm<forgetPasswordType>({
    resolver: zodResolver(forgetPasswordSchema),
    defaultValues: {
      email: "",
    },
  });

  const onSubmit: SubmitHandler<forgetPasswordType> = (data) => {
    console.log("asflafjfksf", data);
  };

  return (
    <Form {...forgetPasswordLogin}>
      <form onSubmit={forgetPasswordLogin.handleSubmit(onSubmit)} noValidate>
        <FormField
          control={forgetPasswordLogin.control}
          name="email"
          render={({ field }) => (
            <FormItem className="h-24">
              <FormLabel>Email</FormLabel>
              <FormControl>
                <Input placeholder="example@email.com" type="email" {...field} className="focus:outline-none"/>
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <Button type="submit" className="!mt-8 w-full">
          Send
        </Button>
      </form>
    </Form>
  );
};

export default ForgetPasswordForm;

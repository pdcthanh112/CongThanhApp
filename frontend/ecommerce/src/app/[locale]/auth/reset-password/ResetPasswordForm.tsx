import { Button } from "@/components/ui/button";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { resetPasswordSchema, resetPasswordType } from "@/models/schema/authSchema";
import { zodResolver } from "@hookform/resolvers/zod";
import React from "react";
import { SubmitHandler, useForm } from "react-hook-form";

const ResetPasswordForm = () => {
  const resetPasswordForm = useForm<resetPasswordType>({
    resolver: zodResolver(resetPasswordSchema),
    defaultValues: {
      password: "",
      confirm: "",
    },
  });

  const onSubmit: SubmitHandler<resetPasswordType> = (data) => {
    console.log("asflafjfksf", data);
  };

  return (
    <Form {...resetPasswordForm}>
      <form onSubmit={resetPasswordForm.handleSubmit(onSubmit)} noValidate>
        <FormField
          control={resetPasswordForm.control}
          name="password"
          render={({ field }) => (
            <FormItem className="h-24">
              <FormLabel>Email</FormLabel>
              <FormControl>
                <Input placeholder="example@email.com" type="password" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

<FormField
          control={resetPasswordForm.control}
          name="password"
          render={({ field }) => (
            <FormItem className="h-24">
              <FormLabel>Email</FormLabel>
              <FormControl>
                <Input placeholder="example@email.com" type="password" {...field} />
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

export default ResetPasswordForm;

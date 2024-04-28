"use client";

import { zodResolver } from "@hookform/resolvers/zod";
import { SubmitHandler, useForm } from "react-hook-form";
import { Button } from "@/components/ui/button";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { Icon } from "@mui/material";
import { Visibility, VisibilityOff } from "@mui/icons-material";

import React, { useState } from "react";
import { loginSchema, loginSchemaType } from "@/models/schema/authSchema";
import Link from "next/link";

const LoginForm = () => {
  const [showPassword, setShowPassword] = useState<boolean>(false);

  const formLogin = useForm<loginSchemaType>({
    resolver: zodResolver(loginSchema),
    defaultValues: {
      email: "",
      password: "",
      fcmToken: "",
    },
  });

  const onSubmit: SubmitHandler<loginSchemaType> = (data) => {
    console.log("asflafjfksf", data);
  };

  return (
    <Form {...formLogin}>
      <form onSubmit={formLogin.handleSubmit(onSubmit)}>
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
              <FormLabel style={{ color: "inherit" }}>Password</FormLabel>
              <FormControl>
                <div className="relative">
                  <Input placeholder="********" type={showPassword ? "password" : "text"} {...field} />
                  <Icon
                    component={showPassword ? VisibilityOff : Visibility}
                    cursor={"pointer"}
                    className="absolute right-3 top-2"
                    onClick={() => setShowPassword(!showPassword)}
                  />
                  <Link href={"/forget-password"} className="absolute right-0 hover:underline" title="Forget password">
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
  );
};

export default LoginForm;

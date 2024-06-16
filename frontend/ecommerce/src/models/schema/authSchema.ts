import { z } from "zod";

export const LoginSchema = z
  .object({
    email: z.string().trim().min(1, "Email is required").email(),
    password: z.string().trim().min(1, "Password is required"),
    remember: z.boolean(),
    fcmToken: z.string().optional(),
  })
  .strict();

export type LoginSchemaType = z.TypeOf<typeof LoginSchema>;

export const RegisterSchema = z
  .object({
    name: z.string().trim().min(2, 'Name is required').max(256),
    email: z.string().trim().min(1, "Email is required").email(),
    phone: z.string(),
    password: z.string().min(8, "Password at least 8 characters").max(100),
    confirm: z.string().min(8, "Confirm at least 8 characters").max(100),
  })
  .strict()
  .superRefine(({ confirm, password }, ctx) => {
    if (confirm !== password) {
      ctx.addIssue({
        code: "custom",
        message: "Confirm does not match",
        path: ["confirm"],
      });
    }
  });

export type RegisterSchemaType = z.TypeOf<typeof RegisterSchema>;

export const ForgetPasswordSchema = z
  .object({
    email: z.string().trim().min(1, "Email is required").email(),
  })
  .strict();

export type ForgetPasswordType = z.TypeOf<typeof ForgetPasswordSchema>;

export const resetPasswordSchema = z
  .object({
    password: z.string().min(8, "Password at least 8 characters"),
    confirm: z.string(),
  })
  .strict()
  .superRefine(({ confirm, password }, ctx) => {
    if (confirm !== password) {
      ctx.addIssue({
        code: "custom",
        message: "Confirm does not match",
        path: ["confirm"],
      });
    }
  });

export type resetPasswordType = z.TypeOf<typeof resetPasswordSchema>;

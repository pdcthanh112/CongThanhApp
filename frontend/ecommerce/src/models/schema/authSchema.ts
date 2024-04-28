import { z } from "zod";

export const loginSchema = z
  .object({
    email: z.string().trim().min(1, "Email is required").email(),
    password: z.string().trim().min(1, "Password is required"),
    fcmToken: z.string().optional(),
  })
  .strict();

export type loginSchemaType = z.TypeOf<typeof loginSchema>;

export const registerSchema = z
  .object({
    name: z.string().trim().min(2).max(256),
    email: z.string().trim().min(1, "Email is required").email(),
    phone: z.string(),
    password: z.string().min(8).max(100),
    confirm: z.string().min(8).max(100),
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

export type registerSchemaType = z.TypeOf<typeof registerSchema>;

export const forgetPasswordSchema = z
  .object({
    email: z.string().trim().min(1, "Email is required").email(),
  })
  .strict();

export type forgetPasswordType = z.TypeOf<typeof forgetPasswordSchema>;

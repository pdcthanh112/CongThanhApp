import { z } from 'zod';
import { useTranslations } from 'next-intl';

export const createLoginSchema = (t: ReturnType<typeof useTranslations>) => {
  return z
    .object({
      email: z
        .string()
        .trim()
        .min(1, t('auth.validation.field_required', { field: t('auth.email') }))
        .email(t('auth.validation.field_invalid', { field: t('auth.email') })),
      password: z
        .string()
        .trim()
        .min(1, t('auth.validation.field_required', { field: t('auth.password') })),
      remember: z.boolean(),
      fcmToken: z.string().optional(),
      provider: z.string(),
    })
    .strict();
};

// export type LoginSchemaType = z.TypeOf<typeof LoginSchema>;
export type LoginSchemaType = z.infer<Awaited<ReturnType<typeof createLoginSchema>>>;

export const createRegisterSchema = (t: ReturnType<typeof useTranslations>) => {
  return z
    .object({
      email: z
        .string()
        .trim()
        .min(1, t('auth.validation.field_required', { field: t('auth.email') }))
        .email(t('auth.validation.field_invalid', { field: t('auth.email') })),
      name: z
        .string()
        .trim()
        .min(2, t('auth.validation.field_required', { field: t('auth.name') }))
        .max(256),
      phone: z
        .string()
        .length(10, t('auth.validation.field_required', { field: t('auth.phone') }))
        .regex(
          /^(\+?\d{1,3}[-.\s]?)?(\(?\d{1,4}\)?[-.\s]?)?[\d\s]{7,15}$/,
          t('auth.validation.field_invalid', { field: t('auth.phone') })
        ),
      password: z
        .string()
        .min(1, t('auth.validation.field_required', { field: t('auth.password') }))
        .min(8, 'Password at least 8 characters')
        .max(32),
      confirm: z.string().min(1, t('auth.validation.field_required', { field: t('auth.confirm') })),
    })
    .strict()
    .superRefine(({ confirm, password }, ctx) => {
      if (confirm !== password) {
        ctx.addIssue({
          code: 'custom',
          message: t('auth.validation.not_match'),
          path: ['confirm'],
        });
      }
    });
};

// export type RegisterSchemaType = z.TypeOf<typeof RegisterSchema>;
export type RegisterSchemaType = z.infer<Awaited<ReturnType<typeof createRegisterSchema>>>;

export const ForgetPasswordSchema = z
  .object({
    email: z.string().trim().min(1, 'Email is required').email(),
  })
  .strict();

export type ForgetPasswordType = z.TypeOf<typeof ForgetPasswordSchema>;

export const resetPasswordSchema = z
  .object({
    password: z.string().min(8, 'Password at least 8 characters'),
    confirm: z.string(),
  })
  .strict()
  .superRefine(({ confirm, password }, ctx) => {
    if (confirm !== password) {
      ctx.addIssue({
        code: 'custom',
        message: 'Confirm does not match',
        path: ['confirm'],
      });
    }
  });

export type resetPasswordType = z.TypeOf<typeof resetPasswordSchema>;

export const createEditProfileSchema = (t: ReturnType<typeof useTranslations>) => {
  return z
    .object({
      // email: z
      //   .string()
      //   .trim()
      //   .min(1, t('auth.validation.field_required', { field: t('auth.email') }))
      //   .email(t('auth.validation.field_invalid', { field: t('auth.email') })),
      name: z
        .string()
        .trim()
        .min(1, t('auth.validation.field_required', { field: t('auth.name') })),
      phone: z
        .string()
        .length(10, t('auth.validation.field_required', { field: t('auth.phone') }))
        .regex(
          /^(\+?\d{1,3}[-.\s]?)?(\(?\d{1,4}\)?[-.\s]?)?[\d\s]{7,15}$/,
          t('auth.validation.field_invalid', { field: t('auth.phone') })
        ),
      gender: z.enum(['MALE', 'FEMALE', 'OTHER']),
    })
    .strict();
};

export type EditProfilechemaType = z.infer<Awaited<ReturnType<typeof createEditProfileSchema>>>;

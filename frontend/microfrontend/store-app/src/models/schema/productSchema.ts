import { z } from 'zod';
import { useTranslations } from 'next-intl';

export const createProductSchema = (t: ReturnType<typeof useTranslations>) => {
  return z.object({
    name: z.string().nonempty(t('auth.validation.field_required', { field: 'Name' })),
    slug: z.string().nonempty(),
    category: z.array(z.string()).min(1, 'at least 1'),
    thumbnail: z
      .instanceof(File, { message: 'Vui lòng chọn một file hợp lệ' })
      .refine((file) => ['image/png', 'image/jpeg', 'image/jpg'].includes(file.type), {
        message: 'Invalid image file type',
      })
      .refine((file) => file.size < 5000000, {
        message: "File can't be bigger than 5MB.",
      }),
    attribute: z.array(
      z.object({
        attributeId: z.string().nonempty('Attribute ID is required'),
        value: z.string().nonempty('Attribute value is required'),
      })
    ),
    variant: z.array(
      z.object({
        sku: z.string().nonempty('aaaaaaaaaaaaaaaaa'),
        gtin: z.string().nonempty(),
        price: z
          .number({ required_error: 'Age is required', invalid_type_error: 'Age must be a number' })
          .int()
          .positive(),
        image: z.array(
          z
            .instanceof(File, { message: 'Vui lòng chọn một file hợp lệ' })
            .refine((file) => ['image/png', 'image/jpeg', 'image/jpg'].includes(file.type), {
              message: 'Invalid image file type',
            })
            .refine((file) => file.size < 5000000, {
              message: "File can't be bigger than 5MB.",
            })
        ),
      })
    ),
    image: z.array(
      z
        .instanceof(File, { message: 'Vui lòng chọn một file hợp lệ' })
        .refine((file) => ['image/png', 'image/jpeg', 'image/jpg'].includes(file.type), {
          message: 'Invalid image file type',
        })
        .refine((file) => file.size < 5000000, {
          message: "File can't be bigger than 5MB.",
        })
    ),
    description: z.string(),
  });
};

export type ProductSchemaType = z.infer<Awaited<ReturnType<typeof createProductSchema>>>;

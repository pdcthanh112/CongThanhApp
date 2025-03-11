import { z } from 'zod';
import { useTranslations } from 'next-intl';

export const createProductSchema = (t: ReturnType<typeof useTranslations>) => {
  return z.object({
    name: z.string().nonempty('requiredd'),
    slug: z.string().nonempty(),
    category: z.array(z.string()).min(1, 'at least 1'),
    thumbnail: z
      .instanceof(File)
      .refine((file) => ['image/png', 'image/jpeg', 'image/jpg', 'image/svg+xml', 'image/gif'].includes(file.type), {
        message: 'Invalid image file type',
      }),
    image: z.instanceof(FileList),
    description: z.string(),
  });
};

export type ProductSchemaType = z.infer<Awaited<ReturnType<typeof createProductSchema>>>;

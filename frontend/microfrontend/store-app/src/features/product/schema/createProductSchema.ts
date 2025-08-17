import { z } from 'zod';

export const productSchema = z.object({
  name: z.string().min(2, { message: 'Name must be at least 2 characters' }).max(100),
  price: z.number().min(0, { message: 'Price must be greater than 0' }).max(1000000),
  description: z.string().min(10, { message: 'Description must be at least 10 characters' }).max(1000).optional(),
  category: z.string().min(2, { message: 'Category is required' }),
  stock: z.number().int().min(0, { message: 'Stock must be non-negative' }).optional(),
});

export type ProductFormData = z.infer<typeof productSchema>;
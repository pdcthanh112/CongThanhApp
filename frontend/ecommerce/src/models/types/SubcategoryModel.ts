import { Category } from "@models/type";

export type Subcategory = {
  id: number;
  name: string;
  category?: Category;
};

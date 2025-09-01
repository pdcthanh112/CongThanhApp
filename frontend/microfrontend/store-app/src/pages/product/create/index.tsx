import { Box } from "@mui/material";
import InformationSession from "./InformationSession";
import AttributeSession from "./AttributeSession";
import { FormProvider, useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  productSchema,
  type ProductFormData,
} from "../../../features/product/schema/createProductSchema";
import VariantSession from "./VariantSession";

export default function CreateProductPage() {
  const form = useForm<ProductFormData>({
    resolver: zodResolver(productSchema),
    defaultValues: {
      name: "",
      price: 0,
      description: "",
      category: "",
      stock: 0,
    },
  });

  const onSubmit = async (data: ProductFormData) => {
    try {
      // Gọi API để tạo sản phẩm
      console.log("Form submitted:", data);
      // await api.createProduct(data);
    } catch (error) {
      console.error("Error submitting form:", error);
    }
  };

  return (
    <Box>
      <FormProvider {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)}>
          <InformationSession />
          <AttributeSession />
          <VariantSession />
        </form>
      </FormProvider>
    </Box>
  );
}

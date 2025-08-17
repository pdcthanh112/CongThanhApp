import { createBrowserRouter } from "react-router-dom";
import CreateProductPage from "./pages/product/create";

export const router = createBrowserRouter([
  {
    path: "/product/create",
    element: <CreateProductPage />,
  },
]);

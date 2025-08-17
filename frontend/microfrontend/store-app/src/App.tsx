import { RouterProvider } from "react-router-dom";
import "./App.css";
import { router } from "./Routes";
import RootLayout from "./components/layout/RootLayout";

function App() {
  return (
    <RootLayout>
      <RouterProvider router={router} />
    </RootLayout>
  );
}

export default App;

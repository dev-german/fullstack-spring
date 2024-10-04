import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { ChakraProvider, Text } from "@chakra-ui/react";
import { createStandaloneToast } from "@chakra-ui/react";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Login from "./components/login/Login.jsx";
import Signup from "./components/signup/Signup.jsx";
import AuthProvider from "./components/context/AuthContext.jsx";

import "./index.css";
import ProtectedRoute from "./components/shared/ProtectedRoute.jsx";
import Customers from "./Customers.jsx";
import Home from "./Home.jsx";

const { ToastContainer } = createStandaloneToast();

const router = createBrowserRouter([
  {
    path: "/",
    element: <Login />
  },
  {
    path: "signup",
    element: <Signup />
  },
  {
    path: "dashboard",
    element: <ProtectedRoute><Home /></ProtectedRoute>
  },
  {
    path: "dashboard/customers",
    element: <ProtectedRoute><Customers /></ProtectedRoute>
  }
])

createRoot(document.getElementById("root")).render(
  <StrictMode>
    <ChakraProvider>
      <AuthProvider>
        <RouterProvider router={router} />
      </AuthProvider>
      <ToastContainer />      
    </ChakraProvider>
  </StrictMode>
);

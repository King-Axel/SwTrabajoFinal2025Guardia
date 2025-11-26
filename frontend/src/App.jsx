import React from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import Auth from "./pages/Auth";
import LoginForm from "./components/LoginForm";
import Logout from "./components/Logout";
import RegisterForm from "./components/RegisterForm";
import Urgencias from "./pages/Urgencias";
import ProtectedRoute from "./components/ProtectedRoute";
import PublicRoute from "./components/PublicRoute";
import UrgenciasLayout from "./pages/UrgenciasLayout";

export default function App() {
  return (
    <Routes>

      {/* Redirección principal */}
      <Route path="/" element={<Navigate to="login" replace />} />

      {/* Layout de autenticación */}
      <Route path="/" element={<Auth />}>
        <Route
          path="login"
          element={
            <PublicRoute>
              <LoginForm />
            </PublicRoute>
          }
        />
        <Route
          path="register"
          element={
            <PublicRoute>
              <RegisterForm />
            </PublicRoute>
          }
        />
      </Route>

      <Route
        path="/logout"
        element={
          <ProtectedRoute>
            <Logout />
          </ProtectedRoute>
        }
      />

      {/* Ruta protegida del módulo de urgencias */}
      <Route
        path="/urgencias"
        element={<UrgenciasLayout />}
      />

      {/* Cualquier ruta desconocida → login */}
      <Route path="*" element={<Navigate to="/login" replace />} />
    </Routes>
  );
}

import React from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import Auth from "./pages/Auth";
import LoginForm from "./components/LoginForm";
import RegisterForm from "./components/RegisterForm";
import Urgencias from "./pages/Urgencias";
import ProtectedRoute from "./components/ProtectedRoute";

export default function App() {
  return (
    <Routes>

      {/* Redirección principal */}
      <Route path="/" element={<Navigate to="login" replace />} />

      {/* Layout de autenticación */}
      <Route path="/" element={<Auth />}>
        <Route path="login" element={<LoginForm />} />
        <Route path="register" element={<RegisterForm />} />
      </Route>

      {/* Ruta protegida del módulo de urgencias */}
      <Route
        path="/urgencias"
        element={
          <ProtectedRoute>
            <Urgencias />
          </ProtectedRoute>
        }
      />

      {/* Cualquier ruta desconocida → login */}
      <Route path="*" element={<Navigate to="/login" replace />} />
    </Routes>
  );
}

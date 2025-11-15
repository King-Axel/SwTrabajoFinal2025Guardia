import React from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import Auth from "./pages/Auth";
import LoginForm from "./components/LoginForm";
import RegisterForm from "./components/RegisterForm";

export default function App() {
  return (
    <Routes>
      <Route path="/" element={<Navigate to="login" replace />} />
      {/*<Route path="/login" element={<Auth />} />*/}
      <Route path="/" element={<Auth />}>
        <Route path="login" element={<LoginForm />} />
        <Route path="register" element={<RegisterForm />}/>
      </Route>

    </Routes>
  );
}
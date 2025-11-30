import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { saveToken } from "../utils/auth";

const LoginForm = ({ onSubmit } = {}) => {
  const [email, setEmail] = useState("");
  const [contrasena, setContrasena] = useState("");
  const [mostrarContra, setMostrarContra] = useState(false);
  const [errors, setErrors] = useState({});
  const navigate = useNavigate();

  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

  const handleSubmit = async (e) => {
    e.preventDefault();

    const newErrors = {};

    if (!email.trim()) {
      newErrors.email = "El correo electrónico es obligatorio";
    } else if (!emailRegex.test(email.trim())) {
      newErrors.email = "Ingrese un correo electrónico válido";
    }

    if (!contrasena) {
      newErrors.contrasena = "La contraseña es obligatoria";
    } else if (contrasena.length < 8) {
      newErrors.contrasena = "La contraseña debe tener al menos 8 caracteres";
    }

    setErrors(newErrors);

    if (Object.keys(newErrors).length > 0) return;
    const payload = { mode: "login", email, contrasena };
    if (onSubmit) onSubmit(payload);

    try {
      const response = await fetch('http://localhost:8081/api/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email: email.trim(), password: contrasena })
      });

      if (!response.ok) {
        const err = await response.json().catch(() => ({}));
        setErrors((prev) => ({
          ...prev,
          server: err.mensaje || "Email o contraseña incorrectos",
        }));
        return;
      }

      const body = await response.json();
      const token = body.token;

      if (!token) {
        setErrors({ server: 'Token no recibido' });
        return;
      }

      saveToken(token);
      navigate('/urgencias');
    } catch (error) {
      setErrors({ server: 'Error de red' });
      console.error("el error es: " + error);
    }

  };

  return (
    <>
      <form
        className="form w-full p-5 xl:flex xl:flex-col lg:p-0 space-y-4"
        onSubmit={handleSubmit}
      >
        <div className="form-block">
          <label htmlFor="login-email">Correo Electrónico</label>
          <div className="relative">
            <i className="bi bi-envelope form-icon absolute left-2"></i>
            <input
              id="login-email"
              type="email"
              value={email}
              placeholder="usuario@ejemplo.com"
              onChange={(e) => {
                setEmail(e.target.value);
                setErrors((prev) => ({ ...prev, email: undefined }));
              }}
              className="input px-3"
            />
          </div>
          {errors.email && (
            <p className="mt-1 text-sm text-red-600">{errors.email}</p>
          )}
        </div>

        <div className="form-block relative">
          <label htmlFor="login-contrasena">Contraseña</label>
          <a href="">
            <span className="absolute lg:top-full right-0 top-1 text-sm text-blue-600 hover:underline">
              ¿Olvidaste tu contraseña?
            </span>
          </a>
          <div className="relative">
            <i
              className={`bi form-icon ${mostrarContra ? "bi-unlock" : "bi-lock"
                } absolute left-2`}
            ></i>
            <input
              id="login-contrasena"
              type={mostrarContra ? "text" : "password"}
              value={contrasena}
              placeholder="Ingrese su contraseña"
              onChange={(e) => {
                setContrasena(e.target.value);
                setErrors((prev) => ({ ...prev, contrasena: undefined }));
              }}
              className="input"
            />
            <button
              type="button"
              onClick={() => setMostrarContra((prev) => !prev)}
              className="absolute right-2 top-1/2 -translate-y-3"
            >
              <i
                className={`bi form-icon ${mostrarContra ? "bi-eye-slash" : "bi-eye"
                  }`}
              ></i>
            </button>
          </div>
          {errors.contrasena && (
            <p className="mt-1 text-sm text-red-600">{errors.contrasena}</p>
          )}
        </div>

        {errors.server && (
          <p className="mt-3 p-2 bg-red-100 text-red-700 rounded">
            {errors.server}
          </p>
        )}

        <button type="submit" className="mt-6 w-full button">
          Ingresar
        </button>
      </form>

      <div className="lg:hidden text-center">
        <div className="flex justify-center gap-1">
          <p>O crea una cuenta con</p>
          <Link to="/register">
            <span className="text-sm text-blue-600 hover:underline">
              Registrarse
            </span>
          </Link>
        </div>
      </div>
    </>
  );
};

export default LoginForm;

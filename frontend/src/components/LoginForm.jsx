import React, { useState } from "react"
import { Link, useNavigate } from 'react-router-dom';

const LoginForm = ({ onSubmit } = {}) => {
  const [email, setEmail] = useState("");
  const [contrasena, setContrasena] = useState("");
  const [mostrarContra, setMostrarContra] = useState("");
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    const payload = { mode: 'login', email, contrasena };
    if (onSubmit) onSubmit(payload);
    navigate('/');
  }

  return (
    <>
      <form className="xl:flex xl:flex-col lg:p-0 form p-5" onSubmit={handleSubmit}>
        <div className='form-block'>
          <label htmlFor="">
            Correo Electrónico
          </label>
          <div className='relative'>
            <i className='bi bi-envelope form-icon absolute left-2'></i>
            <input
              type="email"
              value={email}
              placeholder='usuario@ejemplo.com'
              onChange={(e) => setEmail(e.target.value)}
              className='input px-3'
            />
          </div>
        </div>
        <div className='form-block relative'>
          <label htmlFor="">
            Contraseña
          </label>
          <a href="">
            <span className='absolute lg:top-full right-0 top-1 text-sm text-blue-600 hover:underline'>¿Olvidaste tu contraseña?</span>
          </a>
          <div className='relative'>
            <i className={`bi form-icon ${mostrarContra ? 'bi-unlock' : 'bi-lock'} absolute left-2`}></i>
            <input
              type={mostrarContra ? "text" : "password"}
              value={contrasena}
              placeholder='ingrese su contraseña'
              onChange={(e) => setContrasena(e.target.value)}
              className='input'
            />
            <button
              type='button'
              onClick={() => setMostrarContra((prev) => !prev)}
              className='absolute right-2 top-1/2 -translate-y-3'
            >
              <i className={`bi form-icon ${mostrarContra ? 'bi-eye-slash' : 'bi-eye'}`}></i>
            </button>
          </div>
        </div>
        <button
          type='submit'
          className='xl:absolute bottom-0 lg:mt-4 button'
        >
          Ingresar
        </button>
      </form>

      <div className='lg:hidden text-center'>
        <div className="flex justify-center gap-1">
          <p>O crea una cuenta con</p>
          <Link to="/register">
            <span className='text-sm text-blue-600 hover:underline'>Registrarse</span>
          </Link>
        </div>
      </div>
    </>
  )
}

export default LoginForm;
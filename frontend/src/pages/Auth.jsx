import React from 'react';
import { Outlet, Link, useLocation } from 'react-router-dom';
import '../styles.css'

const Auth = () => {
  const location = useLocation();
  const isLogin = location.pathname.includes('/login');

  return (
    <div className={` ${isLogin? "xl:h-[600px]" : ""} lg:flex lg:flex-row lg:shadow-2xs lg:max-w-[1300px] lg:min-h-[464px] lg:max-h-[700px] lg:items-between lg:white lg:h-auto lg:p-10 lg:mx-10 lg:rounded-xl w-full h-screen bg-gray-50 tex-white flex flex-col justify-center`}>
      <div className={`${isLogin? "" : "lg:justify-center"} lg:w-1/2 lg:flex flex-col hidden`}>
        <div className='flex gap-4 items-center'>
          <i className="bi bi-plus-square text-[28px] text-blue-600" aria-hidden="true" />
          <h2 className='font-bold text-[24px]'>Sistema de guardia</h2>
        </div>
        <img src="src\assets\profesional.png" alt="imagen de un profesional de la salud"
          className='mx-24 my-4 xl:w-[368px] lg:w-60 max-w-[380px]'
        />
        <div>
          <h2 className='font-extrabold text-[24px]'>Gestión de Guardia Hospitalaria</h2>
          <p className='text-gray-500'>Bienvenido, {isLogin? "ingrese a su cuenta" : "rellene los campos"} para comenzar</p>
        </div>
      </div>

      <div className='xl:relative lg:w-1/2 lg:flex lg:h-full flex-col'>
        <nav className={`${isLogin? 'xl:mb-8' : ''} lg:flex hidden group rounded-lg w-full bg-gray-200 px-1 justify-between items-center text-gray-600`}>
          <Link
            to="/login"
            className={`${isLogin ? 'selected' : 'unselected'} option rounded-tl-md rounded-bl-md `}
          >
            Iniciar Sesión
          </Link>
          
          <Link to="/register" className={`${isLogin? "unselected" : "selected"} unselected option rounded-tr-md rounded-br-md`}>Registrarse</Link>
        </nav>
        <div>
          <div className='lg:text-left text-black text-center'>
            <div className="lg:hidden inline-flex items-center justify-center w-16 h-16 bg-blue-100 rounded-full">
              <i className="bi bi-plus-square text-[28px] text-blue-600" aria-hidden="true" />
            </div>
            <div className='mt-8'>
              <h2 className='font-bold text-[24px]'>{isLogin? "Iniciar Sesión" : "Registrarse"}</h2>
              <p className='lg:hidden text-gray-500'>Bienvenido, {isLogin? "ingrese a su cuenta" : "rellene los campos"} para comenzar</p>
            </div>
          </div>

          <Outlet />
        </div>
      </div>
    </div>
  )
}

export default Auth;

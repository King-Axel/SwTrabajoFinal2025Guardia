import React from 'react'
import RegistrarAtencionForm from "../components/RegistrarAtencionForm";

const RegistrarAtencion = () => {
  return (
    <div>
      <h2 className="text-2xl font-bold mb-3">Registro de Atención</h2>
      <p className="text-gray-500 mb-5">Complete los datos del registro</p>

      <div className="bg-white shadow rounded-xl p-6">
        <RegistrarAtencionForm />
      </div>
    </div>
  )
}

export default RegistrarAtencion

import React from "react";
import IngresoUrgenciasForm from "../components/IngresoUrgenciasForm";

export default function Urgencias() {
  return (
    <div className="w-full min-h-screen bg-gray-50 flex justify-center py-10">
      <div className="w-full max-w-5xl mx-auto px-6">
        <div className="flex items-center gap-3 mb-6">
          <i className="bi bi-plus-square text-[30px] text-blue-600"></i>
          <h1 className="text-2xl font-bold text-gray-800">
            Módulo de urgencias
          </h1>
        </div>

        <IngresoUrgenciasForm />
      </div>
    </div>
  );
}


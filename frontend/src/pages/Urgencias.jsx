import React from "react";
import IngresoUrgenciasForm from "../components/IngresoUrgenciasForm";
import "../styles.css";

export default function Urgencias() {
  return (
    <div className="w-full min-h-screen bg-gray-50 flex justify-center lg:items-center">
      <div className="w-full max-w-5xl mx-auto p-4 lg:p-8">
        <header className="mb-6">
          <div className="flex items-center gap-3 mb-2">
            <i className="bi bi-plus-square text-[28px] text-blue-600" aria-hidden="true" />
            <div>
              <h1 className="text-2xl font-bold text-gray-800">Módulo de urgencias</h1>
              <p className="text-sm text-gray-500">
                Registro de admisiones a guardia y priorización por nivel de emergencia.
              </p>
            </div>
          </div>
        </header>

        <IngresoUrgenciasForm />
      </div>
    </div>
  );
}

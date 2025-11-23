import { useState } from "react";
import ColaEspera from "./ColaEspera";
import NuevaAdmision from "./NuevaAdmision";

export default function UrgenciasLayout() {
  const [tab, setTab] = useState("cola");

  return (
    <div className="w-full min-h-screen bg-gray-50 p-6">
      <header className="flex items-center gap-3 mb-6">
        <i className="bi bi-hospital text-[32px] text-blue-600"></i>
        <div>
          <h1 className="text-3xl font-bold text-gray-800">Sistema de Urgencias</h1>
          <p className="text-sm text-gray-500">Módulo de Triaje y Admisión</p>
        </div>
      </header>

      {/* Tabs */}
      <div className="flex gap-2 mb-6">
        <button
          className={`px-4 py-2 rounded-lg border ${
            tab === "cola"
              ? "bg-blue-600 text-white border-blue-600"
              : "bg-white hover:bg-gray-100"
          }`}
          onClick={() => setTab("cola")}
        >
          <i className="bi bi-graph-up-arrow mr-1"></i>
          Cola de Espera
        </button>

        <button
          className={`px-4 py-2 rounded-lg border ${
            tab === "nueva"
              ? "bg-blue-600 text-white border-blue-600"
              : "bg-white hover:bg-gray-100"
          }`}
          onClick={() => setTab("nueva")}
        >
          <i className="bi bi-plus-lg mr-1"></i>
          Nueva Admisión
        </button>
      </div>

      {/* Contenido dinámico según TAB */}
      {tab === "cola" ? <ColaEspera /> : <NuevaAdmision />}
    </div>
  );
}
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { clearToken, getUserRole } from "../utils/auth";

import ColaEspera from "./ColaEspera";
import NuevaAdmision from "./NuevaAdmision";
import RegistrarPaciente from "./RegistrarPaciente";
import ReclamarPaciente from "./ReclamarPaciente";

export default function UrgenciasLayout() {
  const [tab, setTab] = useState("cola");
  const navigate = useNavigate();
  const rol = (getUserRole() || "").toUpperCase();
  const esMedico = rol.includes("MEDICO");

  const handleLogout = () => {
    clearToken();
    navigate("/login", { replace: true });
  };

  return (
    <div className="w-full min-h-screen bg-gray-50 p-6">
      <header className="flex items-center justify-between gap-6 mb-6">
        {/* Izquierda: icono + título */}
        <div className="flex items-center gap-3">
          <i className="bi bi-hospital text-[32px] text-blue-600"></i>
          <div>
            <h1 className="text-3xl font-bold text-gray-800">Sistema de Urgencias</h1>
            <p className="text-sm text-gray-500">Módulo de Triaje y Admisión</p>
          </div>
        </div>

        {/* Derecha: navbar */}
        <div className="rounded-2xl bg-white shadow-sm border border-gray-200 p-2">
          <div className="flex items-center gap-3">
            <div className="inline-flex gap-2">
              <button
                type="button"
                onClick={() => setTab("cola")}
                className={`px-4 py-2 rounded-xl text-sm font-medium transition ${tab === "cola" ? "bg-white shadow text-blue-700" : "text-gray-600 hover:text-gray-800"
                  }`}
              >
                <i className="bi bi-graph-up-arrow mr-2"></i>
                Cola de Espera
              </button>

              {!esMedico && (
                <>
                  <button
                    type="button"
                    onClick={() => setTab("nueva")}
                    className={`px-4 py-2 rounded-xl text-sm font-medium transition ${tab === "nueva" ? "bg-white shadow text-blue-700" : "text-gray-600 hover:text-gray-800"
                      }`}
                  >
                    <i className="bi bi-plus-lg mr-2"></i>
                    Nueva Admisión
                  </button>

                  <button
                    type="button"
                    onClick={() => setTab("paciente")}
                    className={`px-4 py-2 rounded-xl text-sm font-medium transition ${tab === "paciente" ? "bg-white shadow text-blue-700" : "text-gray-600 hover:text-gray-800"
                      }`}
                  >
                    <i className="bi bi-person-plus mr-2"></i>
                    Registrar paciente
                  </button>
                </>
              )}

              {esMedico && (
                <button
                  type="button"
                  onClick={() => setTab("reclamar")}
                  className={`px-4 py-2 rounded-xl text-sm font-medium transition ${tab === "reclamar" ? "bg-white shadow text-blue-700" : "text-gray-600 hover:text-gray-800"
                    }`}
                >
                  <i className="bi bi-clipboard2-heart mr-2"></i>
                  Reclamar paciente
                </button>
              )}
            </div>

            {/* SIEMPRE visible (médico y enfermera) */}
            <button
              type="button"
              onClick={handleLogout}
              className="px-4 py-2 rounded-xl text-sm font-medium transition text-gray-600 hover:text-gray-800"
            >
              <i className="bi bi-box-arrow-right mr-2"></i>
              Salir
            </button>
          </div>
        </div>
      </header>

      {/* Contenido dinámico según TAB */}
      {tab === "cola" && <ColaEspera />}
      {tab === "nueva" && !esMedico && <NuevaAdmision />}
      {tab === "paciente" && !esMedico && <RegistrarPaciente />}
      {tab === "reclamar" && esMedico && <div>Reclamar paciente (pendiente)</div>}
    </div>
  );
}
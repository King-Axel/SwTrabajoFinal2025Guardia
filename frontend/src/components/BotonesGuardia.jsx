import { FiActivity, FiClock } from "react-icons/fi";

export default function BotonesGuardia({ vistaActiva, onCambiarVista }) {
  return (
    <div className="flex  gap-0 mb-3 bg-transparent">
      {/* Botón Cola de Espera */}
      <button
        onClick={() => onCambiarVista("cola")}
        className={`
          flex items-center gap-2 px-5 py-2 
          rounded-l-lg border border-gray-300 
          shadow-sm transition-all duration-200 cursor-pointer
          ${vistaActiva === "cola" 
            ? "bg-blue-500 text-white border-blue-500" 
            : "bg-white text-gray-700 hover:bg-gray-50"
          }
        `}
      >
        <FiActivity 
          className={vistaActiva === "cola" ? "text-white" : "text-gray-700"} 
          size={17} 
        />
        Cola de Espera
      </button>

      {/* Botón Pacientes Atendidos */}
      <button
        onClick={() => onCambiarVista("atendidos")}
        className={`
          flex items-center gap-2 px-5 py-2 
          rounded-r-lg border border-gray-300 border-l-0
          shadow-sm transition-all duration-200 cursor-pointer
          ${vistaActiva === "atendidos" 
            ? "bg-blue-500 text-white border-blue-500" 
            : "bg-white text-gray-700 hover:bg-gray-50"
          }
        `}
      >
        <FiClock 
          className={vistaActiva === "atendidos" ? "text-white" : "text-gray-700"} 
          size={17} 
        />
        Pacientes en Atención
      </button>
    </div>
  );
}
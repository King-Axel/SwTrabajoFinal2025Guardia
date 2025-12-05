import { useEffect, useState } from "react";
import { getToken } from "../utils/auth";
import PacienteCard from "./PacienteCard";

export default function PacientesAtendidos() {
  const [lista, setLista] = useState([]);
  const [loading, setLoading] = useState(true);
  const [apiError, setApiError] = useState("");

  const cargarHistorial = async () => {
    setLoading(true);
    setApiError("");

    try {
      const token = getToken();
      if (!token) {
        setLista([]);
        setApiError("No hay sesión activa");
        return;
      }

      const res = await fetch("http://localhost:8081/api/urgencias/atencion/historial", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      const data = await res.json().catch(() => ({}));

      if (!res.ok) {
        setLista([]);
        setApiError(data?.mensaje || `Error HTTP ${res.status}`);
        return;
      }

      setLista(Array.isArray(data) ? data : []);
    } catch (e) {
      setLista([]);
      setApiError("No se pudo conectar con el servidor");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    cargarHistorial();
  }, []);

  if (loading) return <p>Cargando...</p>;

  return (
    <div>
      <div className="flex items-center justify-between mb-3">
        <button className="button" onClick={cargarHistorial}>
          Actualizar
        </button>
      </div>

      {apiError && (
        <p className="mt-3 p-2 bg-red-100 text-red-700 rounded">
          {apiError}
        </p>
      )}

      {lista.length === 0 ? (
        <div className="text-center p-10 text-gray-400">
          <i className="bi bi-clipboard2-check text-5xl"></i>
          <p className="mt-2">No hay pacientes en atención</p>
        </div>
      ) : (
        <div className="space-y-4">
          {lista.map((ingreso, index) => {
            const estado = ingreso.estado || ingreso.estadoIngreso; // según cómo lo llames
            const esFinalizado = estado === "FINALIZADO";

            return (
              <div key={ingreso?.id ?? index} className="relative">
                {/* Badge según estado */}
                <div
                  className={`absolute -left-2 -top-2 text-white text-xs font-bold px-3 py-1 rounded-full z-10 shadow-md ${
                    esFinalizado ? "bg-green-500" : "bg-blue-500"
                  }`}
                >
                  {esFinalizado ? "FINALIZADO" : "EN PROCESO"}
                </div>

                <PacienteCard
                  paciente={ingreso}
                  seleccionado={false}
                  onSeleccionar={() => {}}
                  posicion={index + 1}
                  esProximo={false}
                />
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
}

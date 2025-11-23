import { useEffect, useState } from "react";
import { getToken } from "../utils/auth";

export default function ColaEspera() {
  const [lista, setLista] = useState([]);
  const [loading, setLoading] = useState(true);

  const cargarDatos = async () => {
    setLoading(true);
    try {
      const token = getToken();
      const res = await fetch("http://localhost:8081/api/urgencias/espera", {
        headers: {
          "Authorization": `Bearer ${token}`
        }
      });
      const data = await res.json();
      setLista(data);
    } catch (err) {
      console.error("Error cargando cola", err);
    }
    setLoading(false);
  };

  useEffect(() => {
    cargarDatos();
  }, []);

  if (loading) return <p>Cargando...</p>;

  return (
    <div>
      <h2 className="text-2xl font-bold mb-3">Cola de Espera</h2>
      <p className="text-gray-500 mb-5">
        Pacientes pendientes de atención ordenados por prioridad
      </p>

      {lista.length === 0 ? (
        <div className="text-center p-10 text-gray-400">
          <i className="bi bi-person text-5xl"></i>
          <p className="mt-2">No hay pacientes en espera</p>
        </div>
      ) : (
        <div className="space-y-4">
          {lista.map((p, index) => (
            <PacienteCard key={index} paciente={p} />
          ))}
        </div>
      )}
    </div>
  );
}

function PacienteCard({ paciente }) {
  const colorPorNivel = {
    "Critica": "border-red-500",
    "Emergencia": "border-orange-500",
    "Urgencia": "border-yellow-500",
    "Urgencia menor": "border-green-500",
    "Sin Urgencia": "border-blue-500"
  };

  return (
    <div className={`border-l-8 ${colorPorNivel[paciente.nivelEmergencia]} bg-white rounded-xl p-5 shadow`}>
      <div className="flex justify-between items-center">
        <div>
          <h3 className="font-bold text-lg">{paciente.apellido} {paciente.nombre}</h3>
          <p className="text-gray-500 text-sm">{paciente.cuil}</p>
        </div>
        <span className="px-3 py-1 rounded-md bg-gray-100 text-gray-700 text-sm">
          {paciente.nivelEmergencia}
        </span>
      </div>

      <p className="mt-3 p-3 bg-gray-100 rounded text-gray-600">{paciente.informe}</p>

      <div className="flex gap-6 mt-3 text-gray-700 text-sm">
        <span><i className="bi bi-thermometer-sun"></i> {paciente.temperatura}°C</span>
        <span><i className="bi bi-heart"></i> {paciente.frecuenciaCardiaca} lpm</span>
        <span><i className="bi bi-wind"></i> {paciente.frecuenciaRespiratoria} rpm</span>
        <span><i className="bi bi-heart-pulse"></i> {paciente.frecuenciaSistolica}/{paciente.frecuenciaDiastolica}</span>
      </div>
    </div>
  );
}

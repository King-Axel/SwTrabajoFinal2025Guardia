import { useEffect, useState } from "react";
import { getToken } from "../utils/auth";

export default function ColaEspera() {
  const [lista, setLista] = useState([]);
  const [loading, setLoading] = useState(true);

  const cargarDatos = async () => {
    setLoading(true);
    try {
      const token = getToken();

      if (!token) {
        setLista([]);
        console.error("No hay token. Redirigí al login.");
        return;
      }

      const res = await fetch("http://localhost:8081/api/urgencias/espera", {
        headers: {
          "Authorization": `Bearer ${token}`
        }
      });

      if (!res.ok) {
        // 403 suele venir sin body JSON -> no hagas res.json()
        const txt = await res.text().catch(() => "");
        throw new Error(txt || `HTTP ${res.status}`);
      }

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
    Critica: { border: "border-red-500", bg: "bg-red-200" },
    Emergencia: { border: "border-orange-500", bg: "bg-orange-200" },
    Urgencia: { border: "border-yellow-500", bg: "bg-yellow-200" },
    "Urgencia menor": { border: "border-green-500", bg: "bg-green-200" },
    "Sin Urgencia": { border: "border-blue-500", bg: "bg-blue-200" },
  };

  // Si viene un Ingreso, el paciente está en paciente.paciente
  const p = paciente.paciente ?? paciente;

  // value objects (según cómo serialice Jackson)
  const temp = paciente.temperatura?.temperatura ?? paciente.temperatura;
  const fc = paciente.frecuenciaCardiaca?.frecuencia ?? paciente.frecuenciaCardiaca;
  const fr = paciente.frecuenciaRespiratoria?.frecuencia ?? paciente.frecuenciaRespiratoria;

  const sist = paciente.frecuenciaArterial?.sistolica ?? paciente.frecuenciaSistolica;
  const diast = paciente.frecuenciaArterial?.diastolica ?? paciente.frecuenciaDiastolica;

  // Enum -> label lindo
  const nivelRaw = paciente.nivelEmergencia;
  const nivel = ({
    CRITICA: "Critica",
    EMERGENCIA: "Emergencia",
    URGENCIA: "Urgencia",
    URGENCIA_MENOR: "Urgencia menor",
    SIN_URGENCIA: "Sin Urgencia",
  }[nivelRaw] ?? nivelRaw);

  return (
    <div className={`border-l-8 ${colorPorNivel[nivel]?.border} bg-white rounded-xl p-5 shadow`}>
      <div className="flex justify-between items-center">
        <div>
          <h3 className="font-bold text-lg">
            {p?.apellido} {p?.nombre}
          </h3>
          <p className="text-gray-500 text-sm">{p?.cuil}</p>
        </div>

        <span className={`px-3 py-1 rounded-md ${colorPorNivel[nivel]?.bg} text-gray-700 text-sm`}>
          {nivel}
        </span>
      </div>

      <p className="mt-3 p-3 bg-gray-100 rounded text-gray-600">
        {paciente.informe}
      </p>

      <div className="flex flex-wrap gap-6 mt-3 text-gray-700 text-sm">
        <span><i className="bi bi-thermometer-sun"></i> {temp}°C</span>
        <span><i className="bi bi-heart"></i> {fc} lpm</span>
        <span><i className="bi bi-wind"></i> {fr} rpm</span>
        <span><i className="bi bi-heart-pulse"></i> {sist}/{diast}</span>
      </div>
    </div>
  );
}


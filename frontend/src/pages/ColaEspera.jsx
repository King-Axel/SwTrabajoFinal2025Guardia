import { useEffect, useState } from "react";
import { getToken, getUserRole } from "../utils/auth";
import BotonesGuardia from "../components/BotonesGuardia";
import PacienteCard from "../components/PacienteCard";
import PacientesAtendidos from "../components/PacientesAtendidos";

export default function ColaEspera() {
  const [tab, setTab] = useState("cola");
  const [lista, setLista] = useState([]);
  const [loading, setLoading] = useState(true);
  const [pacienteSeleccionado, setPacienteSeleccionado] = useState(null);

  const rol = (getUserRole() || "").toUpperCase();
  const esMedico = rol.includes("MEDICO");

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
          Authorization: `Bearer ${token}`, // ✅ FIX
        },
      });

      if (!res.ok) {
        const txt = await res.text().catch(() => "");
        throw new Error(txt || `HTTP ${res.status}`); // ✅ FIX
      }

      const data = await res.json();
      setLista(data);

      // Seleccionar automáticamente el primero
      if (data.length > 0) setPacienteSeleccionado(data[0]);
      else setPacienteSeleccionado(null);
    } catch (err) {
      console.error("Error cargando cola", err);
    } finally {
      setLoading(false);
    }
  };

  const handleReclamarPaciente = async () => {
    // En tu back, reclamar toma SIEMPRE el próximo, no el seleccionado.
    // Igual mantenemos tu UX: si no hay nada seleccionado, no deja reclamar.
    if (!pacienteSeleccionado) {
      alert("No hay pacientes para reclamar");
      return;
    }

    try {
      const token = getToken();
      if (!token) {
        alert("No hay sesión activa");
        return;
      }

      const res = await fetch("http://localhost:8081/api/urgencias/reclamos/proximo", {
        method: "POST",
        headers: {
          Authorization: `Bearer ${token}`, // ✅ FIX
        },
        // ✅ NO body, el back decide el próximo
      });

      const data = await res.json().catch(() => ({}));

      if (!res.ok) {
        // tu back manda {mensaje:"..."} en errores
        throw new Error(data?.mensaje || `Error HTTP ${res.status}`);
      }

      alert("¡Ingreso reclamado exitosamente!");
      // refrescar cola: el reclamado ya no debe aparecer
      cargarDatos();
    } catch (error) {
      console.error("Error reclamando paciente:", error);
      alert(error?.message || "Error al reclamar el paciente.");
    }
  };

  const handleSeleccionarPaciente = (paciente) => {
    setPacienteSeleccionado(paciente);
  };

  useEffect(() => {
    if (tab === "cola") cargarDatos();
  }, [tab]);

  if (loading && tab === "cola") return <p>Cargando...</p>;

  return (
    <div>
      <div className="flex justify-between items-center mb-3">
        <div>
          <h2 className="text-2xl font-bold">
            {tab === "cola" ? "Cola de Espera" : "Pacientes Atendidos"}
          </h2>
          <p className="text-gray-500">
            {tab === "cola"
              ? "Ingresos pendientes de atención ordenados por prioridad"
              : "Historial de ingresos ya atendidos"}
          </p>
        </div>

        {tab === "cola" && esMedico && lista.length > 0 && (
          <div className="flex flex-col items-end gap-2">
            <div className="flex items-center gap-4">
              <button
                onClick={handleReclamarPaciente}
                className={`
                  flex items-center gap-3 px-6 py-3
                  rounded-lg border
                  shadow-md transition hover:shadow-lg
                  ${pacienteSeleccionado
                    ? "border-green-500 bg-green-500 text-white hover:bg-green-600"
                    : "border-gray-300 bg-gray-100 text-gray-400 cursor-not-allowed"
                  }
                `}
                disabled={!pacienteSeleccionado}
              >
                <i className="bi bi-person-check text-xl"></i>
                <span className="font-semibold">Reclamar Paciente</span>
              </button>
            </div>
          </div>
        )}
      </div>

      {esMedico && <BotonesGuardia vistaActiva={tab} onCambiarVista={setTab} />}

      {tab === "cola" ? (
        lista.length === 0 ? (
          <div className="text-center p-10 text-gray-400">
            <i className="bi bi-person text-5xl"></i>
            <p className="mt-2">No hay pacientes en espera</p>
          </div>
        ) : (
          <div className="space-y-4">
            {lista.map((p, index) => (
              <div key={p?.id ?? index} className="relative">
                {index === 0 && (
                  <div className="absolute -left-2 -top-2 bg-green-500 text-white text-xs font-bold px-3 py-1 rounded-full z-10 shadow-md">
                    PRÓXIMO
                  </div>
                )}

                <PacienteCard
                  paciente={p}
                  seleccionado={pacienteSeleccionado?.id === p.id}
                  onSeleccionar={() => handleSeleccionarPaciente(p)}
                  posicion={index + 1}
                  esProximo={index === 0}
                />
              </div>
            ))}
          </div>
        )
      ) : (
        <PacientesAtendidos />
      )}
    </div>
  );
}
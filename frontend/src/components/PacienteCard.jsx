const PacienteCard = ({paciente, now}) => {
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

    const calcularEspera = () => {

    if (!paciente.fechaIngreso) return null;

    const fecha = new Date(paciente.fechaIngreso);
    if (isNaN(fecha.getTime())) return null;

    const ahoraMs = now ?? Date.now();
    const diffMin = Math.floor((ahoraMs - fecha.getTime()) / 60000);

    if (diffMin < 1) return "menos de 1 min";
    if (diffMin < 60) return `${diffMin} min`;

    const horas = Math.floor(diffMin / 60);
    const minutos = diffMin % 60;
    if (minutos === 0) return `${horas} h`;
    return `${horas} h ${minutos} min`;
  };

  const tiempoEspera = calcularEspera();

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
        {tiempoEspera && (
          <span>
            <i className="bi bi-clock-history"></i> Espera: {tiempoEspera}
          </span>
        )}
        <span><i className="bi bi-thermometer-sun"></i> {temp}°C</span>
        <span><i className="bi bi-heart"></i> {fc} lpm</span>
        <span><i className="bi bi-wind"></i> {fr} rpm</span>
        <span><i className="bi bi-heart-pulse"></i> {sist}/{diast}</span>
      </div>
    </div>
  )
}

export default PacienteCard

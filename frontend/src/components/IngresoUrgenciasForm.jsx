import React, { useState } from "react";
import { getToken } from "../utils/auth";

const nivelEmergenciaOptions = [
  { value: "Critica", label: "Crítica (Rojo)" },
  { value: "Emergencia", label: "Emergencia (Naranja)" },
  { value: "Urgencia", label: "Urgencia (Amarillo)" },
  { value: "Urgencia menor", label: "Urgencia menor (Verde)" },
  { value: "Sin Urgencia", label: "Sin Urgencia (Azul)" },
];

export default function IngresoUrgenciasForm() {
  const [formData, setFormData] = useState({
    cuil: "",
    informe: "",
    nivelEmergencia: "",
    temperatura: "",
    frecuenciaCardiaca: "",
    frecuenciaRespiratoria: "",
    frecuenciaSistolica: "",
    frecuenciaDiastolica: "",
  });

  const [errors, setErrors] = useState({});
  const [apiError, setApiError] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [loading, setLoading] = useState(false);
  const [pacienteEncontrado, setPacienteEncontrado] = useState(null);
  const [buscandoPaciente, setBuscandoPaciente] = useState(false);
  const [errorPaciente, setErrorPaciente] = useState("");


  const handleChange = (e) => {
    const { name, value } = e.target;

    setFormData((prev) => ({ ...prev, [name]: value }));
    setErrors((prev) => ({ ...prev, [name]: undefined }));

    if (name === "cuil") {
      setPacienteEncontrado(null);
      setErrorPaciente("");
      setApiError("");
      setSuccessMessage("");
    }
  };


  const validar = () => {
    const newErrors = {};

    if (!formData.cuil.trim()) newErrors.cuil = "El CUIL es obligatorio";
    if (!pacienteEncontrado) newErrors.cuil = "Debe ingresar un CUIL de un paciente existente";
    if (!formData.informe.trim()) newErrors.informe = "El informe es obligatorio";
    if (!formData.nivelEmergencia) newErrors.nivelEmergencia = "Seleccione un nivel";

    const checkNumber = (field, label) => {
      const val = parseFloat(formData[field]);
      if (isNaN(val)) newErrors[field] = `${label} es obligatorio`;
      else if (val < 0) newErrors[field] = `${label} no puede ser negativo`;
    };

    checkNumber("temperatura", "Temperatura");
    checkNumber("frecuenciaCardiaca", "Frecuencia cardíaca");
    checkNumber("frecuenciaRespiratoria", "Frecuencia respiratoria");
    checkNumber("frecuenciaSistolica", "Presión sistólica");
    checkNumber("frecuenciaDiastolica", "Presión diastólica");

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const buscarPaciente = async (cuilValue) => {
    const c = (cuilValue ?? "").trim();

    setPacienteEncontrado(null);
    setErrorPaciente("");

    if (!c) return;

    const token = getToken();
    if (!token) {
      setErrorPaciente("Sesión expirada. Volvé a iniciar sesión.");
      return;
    }

    setBuscandoPaciente(true);
    try {
      const res = await fetch(
        `http://localhost:8081/api/pacientes/${encodeURIComponent(c)}`,
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (res.status === 404) {
        setErrorPaciente(
          "El paciente no existe. Debe registrarlo antes de proceder al ingreso."
        );
        return;
      }

      const data = await res.json().catch(() => ({}));

      if (!res.ok) {
        setErrorPaciente(data?.mensaje || "No se pudo buscar el paciente.");
        return;
      }

      setPacienteEncontrado(data);
    } catch (e) {
      setErrorPaciente("Error de red al buscar el paciente.");
    } finally {
      setBuscandoPaciente(false);
    }
  };

  const normalizeText = (s) =>
    (s ?? "")
      .toString()
      .toLowerCase()
      .normalize("NFD")
      .replace(/[\u0300-\u036f]/g, "");

  const applyBackendFieldError = (msg) => {
    if (!msg) return false;

    const m = normalizeText(msg);

    if (m.includes("temperatura")) {
      setErrors((prev) => ({ ...prev, temperatura: msg }));
      return true;
    }
    if (m.includes("frecuencia cardiaca") || m.includes("cardiaca")) {
      setErrors((prev) => ({ ...prev, frecuenciaCardiaca: msg }));
      return true;
    }
    if (m.includes("frecuencia respiratoria") || m.includes("respiratoria")) {
      setErrors((prev) => ({ ...prev, frecuenciaRespiratoria: msg }));
      return true;
    }
    if (m.includes("presion sistolica") || m.includes("sistolica")) {
      setErrors((prev) => ({ ...prev, frecuenciaSistolica: msg }));
      return true;
    }
    if (m.includes("presion diastolica") || m.includes("diastolica")) {
      setErrors((prev) => ({ ...prev, frecuenciaDiastolica: msg }));
      return true;
    }
    if (m.includes("nivel de emergencia")) {
      setErrors((prev) => ({ ...prev, nivelEmergencia: msg }));
      return true;
    }
    if (m.includes("informe")) {
      setErrors((prev) => ({ ...prev, informe: msg }));
      return true;
    }
    if (m.includes("cuil")) {
      setErrors((prev) => ({ ...prev, cuil: msg }));
      return true;
    }

    return false;
  };


  const handleSubmit = async (e) => {
    e.preventDefault();
    setApiError("");
    setSuccessMessage("");
    setErrors({});

    if (!validar()) return;

    const token = getToken();
    if (!token) {
      setApiError("No hay sesión activa");
      return;
    }

    setLoading(true);

    try {
      const response = await fetch("http://localhost:8081/api/urgencias/ingresos", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({
          paciente: {
            cuil: formData.cuil.trim(),
          },
          informe: formData.informe,
          nivelEmergencia: formData.nivelEmergencia,
          temperatura: parseFloat(formData.temperatura),
          frecuenciaCardiaca: parseFloat(formData.frecuenciaCardiaca),
          frecuenciaRespiratoria: parseFloat(formData.frecuenciaRespiratoria),
          frecuenciaSistolica: parseFloat(formData.frecuenciaSistolica),
          frecuenciaDiastolica: parseFloat(formData.frecuenciaDiastolica),
        }),
      });

      const data = await response.json();

      if (!response.ok) {
        const msg = data?.mensaje || "Error al registrar ingreso";

        // si puede mapearse a un campo, lo mostramos abajo del input
        const mapped = applyBackendFieldError(msg);

        // si no se pudo mapear, queda como error general
        if (!mapped) setApiError(msg);

        return;
      }

      setSuccessMessage(data.mensaje || "Ingreso registrado correctamente");

      setFormData({
        cuil: "",
        informe: "",
        nivelEmergencia: "",
        temperatura: "",
        frecuenciaCardiaca: "",
        frecuenciaRespiratoria: "",
        frecuenciaSistolica: "",
        frecuenciaDiastolica: "",
      });
      setPacienteEncontrado(null);
      setErrorPaciente("");

      setErrors({});
    } catch {
      setApiError("No se pudo conectar con el servidor");
    } finally {
      setLoading(false);
    }
  };

  return (
    <form className="form bg-white shadow rounded-xl p-6 max-w-4xl mx-auto" onSubmit={handleSubmit}>
      <h3 className="text-xl font-semibold">Registrar ingreso a urgencias</h3>

      {/* Identificación */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-4">
        {/* CUIL */}
        <div className="form-block">
          <label>CUIL</label>
          <div className="relative">
            <i className="bi bi-person-vcard form-icon absolute left-2"></i>
            <input
              name="cuil"
              value={formData.cuil}
              onChange={handleChange}
              onBlur={() => buscarPaciente(formData.cuil)}
              className="input"
              placeholder="20-00000000-0"
            />
          </div>

          {buscandoPaciente && (
            <p className="text-gray-500 text-sm mt-1">Buscando paciente...</p>
          )}

          {pacienteEncontrado && (
            <p className="text-green-700 bg-green-50 border border-green-200 rounded p-2 mt-2 text-sm">
              Paciente encontrado: <b>{pacienteEncontrado.apellido}</b>, {pacienteEncontrado.nombre}
            </p>
          )}

          {errorPaciente && (
            <p className="text-red-700 bg-red-50 border border-red-200 rounded p-2 mt-2 text-sm">
              {errorPaciente}
            </p>
          )}

          {errors.cuil && <p className="text-red-600 text-sm">{errors.cuil}</p>}
        </div>
      </div>


      {/* Informe */}
      <div className="form-block">
        <label>Informe</label>
        <div className="relative">
          <i className="bi bi-clipboard-pulse text-2xl absolute left-2 top-3"></i>

          <textarea
            name="informe"
            value={formData.informe}
            onChange={handleChange}
            className="input min-h-[120px] resize-y"
            placeholder="Escribe aquí los síntomas del paciente"
          />
        </div>
        {errors.informe && <p className="text-red-600 text-sm">{errors.informe}</p>}
      </div>

      {/* Nivel emergencia */}
      <div className="form-block">
        <label>Nivel de emergencia</label>
        <div className="relative">
          <i className="bi bi-exclamation-triangle form-icon absolute left-2"></i>

          <select
            name="nivelEmergencia"
            value={formData.nivelEmergencia}
            onChange={handleChange}
            className="input"
          >
            <option value="">Seleccione nivel</option>
            {nivelEmergenciaOptions.map((opt) => (
              <option className="relative" value={opt.value} key={opt.value}>
                {opt.label}
              </option>
            ))}
          </select>
        </div>
        {errors.nivelEmergencia && (
          <p className="text-red-600 text-sm">{errors.nivelEmergencia}</p>
        )}
      </div>

      {/* Signos vitales */}
      <h4 className="font-semibold mt-6">Signos vitales</h4>

      <div className="grid grid-cols-1 lg:grid-cols-3 lg:gap-4">
        <InputNumber label="Temperatura (°C)" name="temperatura" formData={formData} onChange={handleChange} error={errors.temperatura} placeholder="25 - 44°C" icon="thermometer" />
        <InputNumber label="Frecuencia cardíaca (lpm)" name="frecuenciaCardiaca" formData={formData} onChange={handleChange} error={errors.frecuenciaCardiaca} placeholder="20 - 220 lpm" icon="heart-pulse" />
        <InputNumber label="Frecuencia respiratoria (rpm)" name="frecuenciaRespiratoria" formData={formData} onChange={handleChange} error={errors.frecuenciaRespiratoria} placeholder="20 rpm" icon="wind" />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 lg:gap-4">
        <InputNumber label="Presión sistólica (mmHg)" name="frecuenciaSistolica" formData={formData} onChange={handleChange} error={errors.frecuenciaSistolica} placeholder="40 - 250 mmHg" icon="activity" />
        <InputNumber label="Presión diastólica (mmHg)" name="frecuenciaDiastolica" formData={formData} onChange={handleChange} error={errors.frecuenciaDiastolica} placeholder="20 - 150 mmHg" icon="activity" />
      </div>

      {/* mensajes */}
      {apiError && (
        <p className="mt-3 p-2 bg-red-100 text-red-700 rounded">{apiError}</p>
      )}
      {successMessage && (
        <p className="mt-3 p-2 bg-green-100 text-green-700 rounded">
          {successMessage}
        </p>
      )}

      <button type="submit" className="button mt-5" disabled={loading || buscandoPaciente || !pacienteEncontrado}>
        {loading ? "Guardando..." : "Registrar ingreso"}
      </button>
    </form>
  );
}

function InputNumber({ label, name, formData, onChange, error, placeholder = "", icon = "" }) {
  return (
    <div className="form-block">
      <label>{label}</label>

      <div className="relative">
        <i className={`bi bi-${icon} form-icon absolute left-2`}></i>

        <input
          name={name}
          type="number"
          className="input"
          value={formData[name]}
          onChange={onChange}
          placeholder={placeholder}
        />
      </div>
      {error && <p className="text-red-600 text-sm">{error}</p>}
    </div>
  );
}

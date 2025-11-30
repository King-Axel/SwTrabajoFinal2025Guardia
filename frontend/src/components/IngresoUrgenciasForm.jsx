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
    apellido: "",
    nombre: "",
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

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const validar = () => {
    const newErrors = {};

    if (!formData.cuil.trim()) newErrors.cuil = "El CUIL es obligatorio";
    if (!formData.apellido.trim()) newErrors.apellido = "El apellido es obligatorio";
    if (!formData.nombre.trim()) newErrors.nombre = "El nombre es obligatorio";
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

  const handleSubmit = async (e) => {
    e.preventDefault();
    setApiError("");
    setSuccessMessage("");

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
            apellido: formData.apellido.trim(),
            nombre: formData.nombre.trim(),
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
        setApiError(data.mensaje || "Error al registrar ingreso");
        return;
      }

      setSuccessMessage(data.mensaje || "Ingreso registrado correctamente");

      setFormData({
        cuil: "",
        apellido: "",
        nombre: "",
        informe: "",
        nivelEmergencia: "",
        temperatura: "",
        frecuenciaCardiaca: "",
        frecuenciaRespiratoria: "",
        frecuenciaSistolica: "",
        frecuenciaDiastolica: "",
      });

      setErrors({});
    } catch {
      setApiError("No se pudo conectar con el servidor");
    } finally {
      setLoading(false);
    }
  };

  return (
    <form className="form bg-white shadow rounded-xl p-6" onSubmit={handleSubmit}>
      <h3 className="text-xl font-semibold mb-4">Registrar ingreso a urgencias</h3>

      {/* Identificación */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
        {/* CUIL */}
        <div className="form-block">
          <label>CUIL</label>
          <div className="relative">
            <i className="bi bi-person form-icon absolute left-2"></i>
            <input
              name="cuil"
              value={formData.cuil}
              onChange={handleChange}
              className="input"
              placeholder="20-00000000-0"
            />
          </div>
          {errors.cuil && <p className="text-red-600 text-sm">{errors.cuil}</p>}
        </div>

        {/* Apellido */}
        <div className="form-block">
          <label>Apellido</label>
          <input
            name="apellido"
            value={formData.apellido}
            onChange={handleChange}
            className="input"
          />
          {errors.apellido && <p className="text-red-600 text-sm">{errors.apellido}</p>}
        </div>

        {/* Nombre */}
        <div className="form-block">
          <label>Nombre</label>
          <input
            name="nombre"
            value={formData.nombre}
            onChange={handleChange}
            className="input"
          />
          {errors.nombre && <p className="text-red-600 text-sm">{errors.nombre}</p>}
        </div>
      </div>

      {/* Informe */}
      <div className="form-block">
        <label>Informe</label>
        <textarea
          name="informe"
          value={formData.informe}
          onChange={handleChange}
          className="input min-h-[90px] resize-y"
        />
        {errors.informe && <p className="text-red-600 text-sm">{errors.informe}</p>}
      </div>

      {/* Nivel emergencia */}
      <div className="form-block">
        <label>Nivel de emergencia</label>
        <select
          name="nivelEmergencia"
          value={formData.nivelEmergencia}
          onChange={handleChange}
          className="input"
        >
          <option value="">Seleccione nivel</option>
          {nivelEmergenciaOptions.map((opt) => (
            <option value={opt.value} key={opt.value}>
              {opt.label}
            </option>
          ))}
        </select>
        {errors.nivelEmergencia && (
          <p className="text-red-600 text-sm">{errors.nivelEmergencia}</p>
        )}
      </div>

      {/* Signos vitales */}
      <h4 className="font-semibold mt-6 mb-2">Signos vitales</h4>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
        <InputNumber label="Temperatura (°C)" name="temperatura" formData={formData} onChange={handleChange} error={errors.temperatura}/>
        <InputNumber label="Frecuencia cardíaca (lpm)" name="frecuenciaCardiaca" formData={formData} onChange={handleChange} error={errors.frecuenciaCardiaca}/>
        <InputNumber label="Frecuencia respiratoria (rpm)" name="frecuenciaRespiratoria" formData={formData} onChange={handleChange} error={errors.frecuenciaRespiratoria}/>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <InputNumber label="Presión sistólica (mmHg)" name="frecuenciaSistolica" formData={formData} onChange={handleChange} error={errors.frecuenciaSistolica}/>
        <InputNumber label="Presión diastólica (mmHg)" name="frecuenciaDiastolica" formData={formData} onChange={handleChange} error={errors.frecuenciaDiastolica}/>
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

      <button type="submit" className="button mt-5">
        {loading ? "Guardando..." : "Registrar ingreso"}
      </button>
    </form>
  );
}

function InputNumber({ label, name, formData, onChange, error }) {
  return (
    <div className="form-block">
      <label>{label}</label>
      <input
        name={name}
        type="number"
        className="input"
        value={formData[name]}
        onChange={onChange}
      />
      {error && <p className="text-red-600 text-sm">{error}</p>}
    </div>
  );
}

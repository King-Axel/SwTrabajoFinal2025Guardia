import React, { useEffect, useState } from "react";
import { getToken } from "../utils/auth";

export default function RegistrarAtencionForm() {
  const [formData, setFormData] = useState({
    ingresoId: "",
    informeAtencion: "",
  });

  const [ingresos, setIngresos] = useState([]); // opciones del select
  const [errors, setErrors] = useState({});
  const [apiError, setApiError] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [loading, setLoading] = useState(false);
  const [loadingIngresos, setLoadingIngresos] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((p) => ({ ...p, [name]: value }));
    setApiError("");
    setSuccessMessage("");
  };

  // Cargar ingresos "en atención" (EN_PROCESO) para el médico
  useEffect(() => {
    const token = getToken();
    if (!token) return;

    const fetchIngresos = async () => {
      setLoadingIngresos(true);
      setApiError("");
      try {
        // 🔧 Ajustá esta URL al endpoint real que tengas para listar "en proceso"
        // Sugerencia: GET /api/urgencias/en-proceso
        const res = await fetch("http://localhost:8081/api/urgencias/en-proceso", {
          headers: { Authorization: `Bearer ${token}` },
        });

        const data = await res.json().catch(() => []);
        if (!res.ok) {
          setIngresos([]);
          setApiError(data?.mensaje || "No se pudieron cargar los ingresos en atención");
          return;
        }

        setIngresos(Array.isArray(data) ? data : []);
      } catch {
        setIngresos([]);
        setApiError("No se pudo conectar con el servidor");
      } finally {
        setLoadingIngresos(false);
      }
    };

    fetchIngresos();
  }, []);

  const validar = () => {
  const newErrors = {};

  if (!formData.ingresoId || !formData.ingresoId.toString().trim()) {
    newErrors.ingresoId = "Seleccioná un ingreso";
  }

  if (!formData.informeAtencion.trim()) {
    newErrors.informeAtencion = "El informe de atención es obligatorio";
  }

  setErrors(newErrors);
  return Object.keys(newErrors).length === 0;
  };

  const buildPayload = () => ({
    ingreso: {
      id: formData.ingresoId,                   
    },
    informe: formData.informeAtencion.trim(),   
  });

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
      // Ajustá la URL al endpoint real de registrar atención
      const res = await fetch("http://localhost:8081/api/urgencias/atencion", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(buildPayload()),
      });

      const data = await res.json().catch(() => ({}));

      if (!res.ok) {
        setApiError(data?.mensaje || "Error al registrar la atención");
        return;
      }

      setSuccessMessage(data?.mensaje || "Atención registrada");
      setFormData({ ingresoId: "", informeAtencion: "" });
      setErrors({});

      // (Opcional) Recargar opciones para que el ingreso atendido deje de aparecer
      // si tu back lo pasa a ATENDIDO y ya no queda EN_PROCESO
      setLoadingIngresos(true);
      const res2 = await fetch("http://localhost:8081/api/urgencias/en-proceso", {
        headers: { Authorization: `Bearer ${token}` },
      });
      const data2 = await res2.json().catch(() => []);
      if (res2.ok) setIngresos(Array.isArray(data2) ? data2 : []);
      setLoadingIngresos(false);
      
    } catch {
      setApiError("No se pudo conectar con el servidor");
    } finally {
      setLoading(false);
    }
  };

  return (
    <form className="form bg-white shadow rounded-xl p-6 max-w-3xl mx-auto" onSubmit={handleSubmit}>
      <h3 className="text-xl font-semibold mb-4">Registrar atención</h3>

      {/* Select de ingreso */}
      <div className="grid grid-cols-1 lg:grid-cols-3 lg:gap-4">
        <SelectField
          label="Ingreso"
          name="ingresoId"
          value={formData.ingresoId}
          onChange={handleChange}
          error={errors.ingresoId}
          icon="person-lines-fill"
          disabled={loadingIngresos || ingresos.length === 0}
          options={[
            {
              value: "",
              label: loadingIngresos
                ? "Cargando ingresos..."
                : ingresos.length === 0
                ? "Seleccionar..."
                : "Seleccionar...",
            },
            ...ingresos.map((ing) => ({
              value: ing.id,                   
              label: buildIngresoLabel(ing),   
            })),
          ]}
        />

      </div>

      {/* Informe */}
      <div className="mt-4">
        <TextAreaField
          label="Informe de atención"
          name="informeAtencion"
          value={formData.informeAtencion}
          onChange={handleChange}
          error={errors.informeAtencion}
          placeholder="Describí evaluación, conducta, indicaciones..."
          icon="file-earmark-text"
          rows={5}
        />
      </div>

      {apiError && <p className="mt-3 p-2 bg-red-100 text-red-700 rounded">{apiError}</p>}
      {successMessage && <p className="mt-3 p-2 bg-green-100 text-green-700 rounded">{successMessage}</p>}

      <button type="submit" className="button mt-5" disabled={loading}>
        {loading ? "Guardando..." : "Registrar atención"}
      </button>
    </form>
  );
}

/* ---------- helpers ---------- */

// Arma un label lindo según el DTO que suele devolver tu back.
// Se banca que falten campos.
function buildIngresoLabel(ing) {
  const apellido = ing?.paciente?.apellido || "";
  const nombre = ing?.paciente?.nombre || "";
  const cuil = ing?.paciente?.cuil || "";
  const motivo = (ing?.informe || "").trim(); // en tu cola se usa "informe" como motivo
  const nivel = ing?.nivelEmergencia || "";

  const persona = [apellido, nombre].filter(Boolean).join(" ").trim();
  const ident = [persona || "Paciente", cuil ? `(${cuil})` : ""].filter(Boolean).join(" ");

  const extra = [nivel ? ` - ${nivel}` : "", motivo ? ` - ${motivo}` : ""].join("");

  return `${ident}${extra}`;
}

/* ---------- fields (igual estilo) ---------- */

function SelectField({
  label,
  name,
  value,
  onChange,
  error,
  icon = "",
  options = [],
  disabled = false,
}) {
  return (
    <div className="form-block">
      <label>{label}</label>

      <div className="relative">
        <i className={`bi bi-${icon} form-icon absolute left-2`}></i>

        <select
          name={name}
          className="input pr-2"
          value={value}
          onChange={onChange}
          disabled={disabled}
        >
          {options.map((op) => (
            <option key={`${op.value}`} value={op.value}>
              {op.label}
            </option>
          ))}
        </select>
      </div>

      {error && <p className="text-red-600 text-sm">{error}</p>}
    </div>
  );
}

function TextAreaField({ label, name, value, onChange, error, placeholder, icon = "", rows = 4 }) {
  return (
    <div className="form-block">
      <label>{label}</label>

      <div className="relative">
        <i className={`bi bi-${icon} form-icon absolute left-2`}></i>

        <textarea
          name={name}
          rows={rows}
          className="input pr-2"
          value={value}
          onChange={onChange}
          placeholder={placeholder}
        />
      </div>

      {error && <p className="text-red-600 text-sm">{error}</p>}
    </div>
  );
}
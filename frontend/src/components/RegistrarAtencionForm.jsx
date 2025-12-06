import React, { useEffect, useMemo, useState } from "react";
import { getToken } from "../utils/auth";

export default function RegistrarAtencionForm() {
  const [formData, setFormData] = useState({
    ingresoId: "",
    informeAtencion: "",
  });

  const [ingresos, setIngresos] = useState([]);
  const [errors, setErrors] = useState({});
  const [apiError, setApiError] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [loading, setLoading] = useState(false);
  const [loadingIngresos, setLoadingIngresos] = useState(true);

  const selectedIngreso = useMemo(() => {
    if (!formData.ingresoId) return null;
    return ingresos.find((i) => String(i.id) === String(formData.ingresoId)) || null;
  }, [formData.ingresoId, ingresos]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((p) => ({ ...p, [name]: value }));
    setApiError("");
    setSuccessMessage("");
  };

  const fetchIngresos = async () => {
    const token = getToken();
    if (!token) {
      setIngresos([]);
      setApiError("No hay sesión activa");
      setLoadingIngresos(false);
      return;
    }

    setLoadingIngresos(true);
    setApiError("");
    try {
      const res = await fetch("http://localhost:8081/api/urgencias/en-proceso", {
        headers: { Authorization: `Bearer ${token}` },
      });

      const data = await res.json().catch(() => []);
      if (!res.ok) {
        setIngresos([]);
        setApiError(data?.mensaje || "No se pudieron cargar los pacientes en atención");
        return;
      }

      const lista = Array.isArray(data) ? data : [];
      const soloEnProceso = lista.filter(
        (ing) => (ing.estado || ing.estadoIngreso) === "EN_PROCESO"
      );

      setIngresos(soloEnProceso);

      // si el seleccionado ya no está, lo limpiamos
      setFormData((prev) => {
        const existe = soloEnProceso.some((i) => String(i.id) === String(prev.ingresoId));
        return existe ? prev : { ...prev, ingresoId: "" };
      });
    } catch {
      setIngresos([]);
      setApiError("No se pudo conectar con el servidor");
    } finally {
      setLoadingIngresos(false);
    }
  };

  useEffect(() => {
    fetchIngresos();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const validar = () => {
    const newErrors = {};

    if (!formData.ingresoId || !String(formData.ingresoId).trim()) {
      newErrors.ingresoId = "Seleccione un paciente";
    }

    if (!formData.informeAtencion || !formData.informeAtencion.trim()) {
      newErrors.informeAtencion = "Falta el dato Informe";
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const buildPayload = () => ({
    ingreso: { id: String(formData.ingresoId).trim() },
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

      // refrescar lista para que el paciente “desaparezca” si pasa a FINALIZADO
      await fetchIngresos();
    } catch {
      setApiError("No se pudo conectar con el servidor");
    } finally {
      setLoading(false);
    }
  };

  return (
    <form className="form bg-white shadow rounded-xl p-6 max-w-4xl mx-auto" onSubmit={handleSubmit}>
      <h3 className="text-xl font-semibold mb-4">Datos de atención</h3>

      <div className="grid grid-cols-1 lg:grid-cols-3 lg:gap-4">
        <SelectField
          label="Paciente"
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
                ? "Cargando..."
                : ingresos.length === 0
                  ? "No hay pacientes en atención"
                  : "Seleccionar...",
            },
            ...ingresos.map((ing) => ({
              value: String(ing.id),
              label: buildIngresoLabel(ing),
            })),
          ]}
        />
      </div>

      {selectedIngreso && (
        <div className="mt-4 p-4 rounded-xl border bg-gray-50">
          <div className="flex items-center justify-between gap-3">
            <div className="font-semibold">
              {selectedIngreso?.paciente?.apellido} {selectedIngreso?.paciente?.nombre}{" "}
              <span className="text-gray-500 font-normal">
                {selectedIngreso?.paciente?.cuil ? `(${selectedIngreso.paciente.cuil})` : ""}
              </span>
            </div>

            <span
              className={`text-xs font-bold px-3 py-1 rounded-full ${triageBadgeClass(
                selectedIngreso?.nivelEmergencia
              )}`}
            >
              {triageLabel(selectedIngreso?.nivelEmergencia)}
            </span>
          </div>

          {!!String(selectedIngreso?.informe || "").trim() && (
            <p className="text-sm text-gray-700 mt-2">
              <span className="font-semibold">Motivo:</span>{" "}
              {String(selectedIngreso.informe).trim()}
            </p>
          )}
        </div>
      )}

      <div className="mt-4">
        <TextAreaField
          label="Informe"
          name="informeAtencion"
          value={formData.informeAtencion}
          onChange={handleChange}
          error={errors.informeAtencion}
          placeholder="Descripción de evaluación, conducta, indicaciones..."
          rows={5}
        />
      </div>

      {apiError && <p className="mt-3 p-2 bg-red-100 text-red-700 rounded">{apiError}</p>}
      {successMessage && <p className="mt-3 p-2 bg-green-100 text-green-700 rounded">{successMessage}</p>}

      <button type="submit" className="button mt-5" disabled={loading || loadingIngresos}>
        {loading ? "Guardando..." : "Registrar atención"}
      </button>
    </form>
  );
}

/* ---------- helpers ---------- */

function buildIngresoLabel(ing) {
  const apellido = ing?.paciente?.apellido || "";
  const nombre = ing?.paciente?.nombre || "";
  const cuil = ing?.paciente?.cuil || "";

  const persona = [apellido, nombre].filter(Boolean).join(" ").trim();
  return [persona || "Paciente", cuil ? `(${cuil})` : ""].filter(Boolean).join(" ");
}


/* ---------- fields (estilo RegistrarPacienteForm) ---------- */

function SelectField({ label, name, value, onChange, error, icon = "", options = [], disabled = false }) {
  return (
    <div className="form-block">
      <label>{label}</label>

      <div className="relative w-100">
        <i className={`bi bi-${icon} form-icon absolute left-2`}></i>

        <select name={name} className="input pr-2" value={value} onChange={onChange} disabled={disabled}>
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

function normNivel(nivel) {
  const n = String(nivel || "").trim().toUpperCase();
  if (n === "CRÍTICA" || n === "CRITICA") return "CRITICA";
  if (n === "EMERGENCIA") return "EMERGENCIA";
  if (n === "URGENCIA") return "URGENCIA";
  if (n === "URGENCIA MENOR" || n === "URGENCIA_MENOR") return "URGENCIA_MENOR";
  if (n === "SIN URGENCIA" || n === "SIN_URGENCIA") return "SIN_URGENCIA";
  return n;
}

function triageLabel(nivel) {
  const n = normNivel(nivel);
  return (
    {
      CRITICA: "Crítica",
      EMERGENCIA: "Emergencia",
      URGENCIA: "Urgencia",
      URGENCIA_MENOR: "Urgencia menor",
      SIN_URGENCIA: "Sin urgencia",
    }[n] || String(nivel || "Sin clasificar")
  );
}

function triageBadgeClass(nivel) {
  const n = normNivel(nivel);
  return (
    {
      CRITICA: "bg-red-600 text-white",
      EMERGENCIA: "bg-orange-500 text-white",
      URGENCIA: "bg-yellow-500 text-white",
      URGENCIA_MENOR: "bg-green-600 text-white",
      SIN_URGENCIA: "bg-blue-600 text-white",
    }[n] || "bg-gray-200 text-gray-700"
  );
}

function TextAreaField({
  label,
  name,
  value,
  onChange,
  error,
  placeholder,
  rows = 4,
  icon = "bi bi-clipboard-pulse",
}) {
  return (
    <div className="form-block">
      <label>{label}</label>

      <div className="relative">
        <i className={`${icon} text-2xl absolute left-2 top-3`} />

        <textarea
          name={name}
          rows={rows}
          className="input pl-10 pr-2"
          value={value}
          onChange={onChange}
          placeholder={placeholder}
        />
      </div>

      {error && <p className="text-red-600 text-sm">{error}</p>}
    </div>
  );
}


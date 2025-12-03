import React, { useState, useEffect } from "react";
import { getToken } from "../utils/auth";

export default function RegistrarAtencionForm() {
  const [pacientes, setPacientes] = useState([]);
  const [formData, setFormData] = useState({
    pacienteId: "",
    medico: "",
    informe: "",
  });

  const [errors, setErrors] = useState({});
  const [apiError, setApiError] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [loading, setLoading] = useState(false);

  // Cargar lista de pacientes
  useEffect(() => {
    async function loadPacientes() {
      try {
        const res = await fetch("http://localhost:8081/api/pacientes");
        const data = await res.json();
        setPacientes(data || []);
      } catch {
        setApiError("No se pudo cargar la lista de pacientes");
      }
    }
    loadPacientes();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((p) => ({ ...p, [name]: value }));
    setApiError("");
    setSuccessMessage("");
  };

  const validar = () => {
    const newErrors = {};
    if (!formData.pacienteId) newErrors.pacienteId = "Debe seleccionar un paciente";
    if (!formData.medico.trim()) newErrors.medico = "El nombre del médico es obligatorio";
    if (!formData.informe.trim()) newErrors.informe = "El informe es obligatorio";

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!validar()) return;

    const token = getToken();
    if (!token) {
      setApiError("No hay sesión activa");
      return;
    }

    setLoading(true);

    try {
      const res = await fetch("http://localhost:8081/api/atenciones", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`
        },
        body: JSON.stringify(formData),
      });

      const data = await res.json().catch(() => ({}));

      if (!res.ok) {
        setApiError(data?.mensaje || "Error al registrar la atención");
        return;
      }

      setSuccessMessage("Atención registrada con éxito");

      setFormData({
        pacienteId: "",
        medico: "",
        informe: "",
      });

      setErrors({});
    } catch {
      setApiError("No se pudo conectar con el servidor");
    } finally {
      setLoading(false);
    }
  };

  return (
    <form className="form bg-white shadow rounded-xl p-6 max-w-2xl mx-auto" onSubmit={handleSubmit}>
      <h3 className="text-xl font-semibold mb-4">Registrar Informe de Atención</h3>
        {/* MÉDICO */}
       

      {/* PACIENTE */}
      <div className="form-block">
        <label>Paciente *</label>
        <select
          name="pacienteId"
          className="input"
          value={formData.pacienteId}
          onChange={handleChange}
        >
          <option value="">Seleccionar paciente</option>
          {pacientes.map((p) => (
            <option key={p.id} value={p.id}>
              {p.nombre} {p.apellido} – {p.motivoConsulta || "Sin detalle"}
            </option>
          ))}
        </select>
        {errors.pacienteId && <p className="text-red-600 text-sm">{errors.pacienteId}</p>}
      </div>
      

      {/* INFORME */}
      <div className="form-block mt-4">
        <label>Informe de Atención *</label>
        <textarea
          name="informe"
          className="input h-32"
          placeholder="Descripción detallada de la atención brindada..."
          value={formData.informe}
          onChange={handleChange}
        />
        {errors.informe && <p className="text-red-600 text-sm">{errors.informe}</p>}
      </div>

      {/* MENSAJES */}
      {apiError && <p className="mt-3 p-2 bg-red-100 text-red-700 rounded">{apiError}</p>}
      {successMessage && <p className="mt-3 p-2 bg-green-100 text-green-700 rounded">{successMessage}</p>}

      {/* BOTÓN */}
      <button type="submit" className="button mt-5" disabled={loading}>
        {loading ? "Guardando..." : "Registrar Atención"}
      </button>
    </form>
  );
}
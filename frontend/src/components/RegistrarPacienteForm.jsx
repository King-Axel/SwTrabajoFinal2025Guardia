import React, { useState } from "react";
import { getToken } from "../utils/auth";

export default function RegistrarPacienteForm() {
  const [formData, setFormData] = useState({
    cuil: "",
    apellido: "",
    nombre: "",
    calle: "",
    numeroCalle: "",
    localidad: "",
    obraSocialId: "",
    numeroAfiliado: "",
  });

  const [errors, setErrors] = useState({});
  const [apiError, setApiError] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((p) => ({ ...p, [name]: value }));
    setApiError("");
    setSuccessMessage("");
  };

  const validar = () => {
    const newErrors = {};
    if (!formData.cuil.trim()) newErrors.cuil = "El CUIL es obligatorio";
    if (!formData.apellido.trim()) newErrors.apellido = "El apellido es obligatorio";
    if (!formData.nombre.trim()) newErrors.nombre = "El nombre es obligatorio";

    // Domicilio: OBLIGATORIO
    if (!formData.calle.trim()) newErrors.calle = "La calle es obligatoria";

    if (!formData.localidad.trim()) newErrors.localidad = "La localidad es obligatoria";

    // numeroCalle: obligatorio y > 0
    const n = parseInt(formData.numeroCalle, 10);
    if (!formData.numeroCalle.toString().trim()) {
      newErrors.numeroCalle = "El número es obligatorio";
    } else if (Number.isNaN(n) || n <= 0) {
      newErrors.numeroCalle = "Número de calle inválido";
    }

    // Afiliación: si completa uno, exigimos ambos
    const algunoAfiliacion = formData.obraSocialId.toString().trim() || formData.numeroAfiliado.trim();
    if (algunoAfiliacion) {
      const id = parseInt(formData.obraSocialId, 10);
      if (Number.isNaN(id) || id <= 0) newErrors.obraSocialId = "Obra social ID inválido";
      if (!formData.numeroAfiliado.trim()) newErrors.numeroAfiliado = "Número de afiliado obligatorio";
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const buildPayload = () => {
    const payload = {
      cuil: formData.cuil.trim(),
      apellido: formData.apellido.trim(),
      nombre: formData.nombre.trim(),
    };

    payload.domicilio = {
      calle: formData.calle.trim(),
      numeroCalle: parseInt(formData.numeroCalle, 10),
      localidad: formData.localidad.trim(),
    };


    const algunoAfiliacion = formData.obraSocialId.toString().trim() || formData.numeroAfiliado.trim();
    if (algunoAfiliacion) {
      payload.afiliado = {
        obraSocial: { id: parseInt(formData.obraSocialId, 10) },
        numeroAfiliado: formData.numeroAfiliado.trim(),
      };
    }

    return payload;
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
      const res = await fetch("http://localhost:8081/api/pacientes", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(buildPayload()),
      });

      const data = await res.json().catch(() => ({}));

      if (!res.ok) {
        setApiError(data?.mensaje || "Error al registrar paciente");
        return;
      }

      setSuccessMessage(data?.mensaje || "Paciente creado");
      setFormData({
        cuil: "",
        apellido: "",
        nombre: "",
        calle: "",
        numeroCalle: "",
        localidad: "",
        obraSocialId: "",
        numeroAfiliado: "",
      });
      setErrors({});
    } catch {
      setApiError("No se pudo conectar con el servidor");
    } finally {
      setLoading(false);
    }
  };

  return (
    <form className="form bg-white shadow rounded-xl p-6 max-w-3xl mx-auto" onSubmit={handleSubmit}>
      <h3 className="text-xl font-semibold mb-4">Registrar paciente</h3>

      <div className="grid grid-cols-1 lg:grid-cols-3 lg:gap-4">
        <Field label="CUIL" name="cuil" value={formData.cuil} onChange={handleChange} error={errors.cuil} placeholder="20-00000000-0" icon="person-vcard" />
        <Field label="Apellido" name="apellido" value={formData.apellido} onChange={handleChange} error={errors.apellido} placeholder="Pérez" icon="person" />
        <Field label="Nombre" name="nombre" value={formData.nombre} onChange={handleChange} error={errors.nombre} placeholder="Juan" icon="person" />
      </div>

      <h4 className="font-semibold mt-6 mb-2">Domicilio</h4>
      <div className="grid grid-cols-1 lg:grid-cols-3 lg:gap-4">
        <Field label="Calle" name="calle" value={formData.calle} onChange={handleChange} error={errors.calle} placeholder="25 de Mayo" icon="signpost-split" />
        <Field label="Número" name="numeroCalle" type="number" value={formData.numeroCalle} onChange={handleChange} error={errors.numeroCalle} placeholder="450" icon="123" />
        <Field label="Localidad" name="localidad" value={formData.localidad} onChange={handleChange} error={errors.localidad} placeholder="San Miguel de Tucumán" icon="geo-alt" />
      </div>

      <h4 className="font-semibold mt-6">Afiliación (opcional)</h4>
      <div className="grid grid-cols-1 lg:grid-cols-2 lg:gap-4">
        <Field label="Obra social ID" name="obraSocialId" type="number" value={formData.obraSocialId} onChange={handleChange} error={errors.obraSocialId} icon="hash" />
        <Field label="Número de afiliado" name="numeroAfiliado" value={formData.numeroAfiliado} onChange={handleChange} error={errors.numeroAfiliado} icon="hash" />
      </div>

      {apiError && <p className="mt-3 p-2 bg-red-100 text-red-700 rounded">{apiError}</p>}
      {successMessage && <p className="mt-3 p-2 bg-green-100 text-green-700 rounded">{successMessage}</p>}

      <button type="submit" className="button mt-5" disabled={loading}>
        {loading ? "Guardando..." : "Registrar paciente"}
      </button>
    </form>
  );
}

function Field({ label, name, value, onChange, error, type = "text", placeholder, icon = "" }) {
  return (
    <div className="form-block">
      <label>{label}</label>

      <div className="relative">
        <i className={`bi bi-${icon} form-icon absolute left-2`}></i>

        <input
          name={name}
          type={type}
          className="input pr-2"
          value={value}
          onChange={onChange}
          placeholder={placeholder}
          {...(type === "number" ? {min: 1} : {})}
        />
      </div>
      {error && <p className="text-red-600 text-sm">{error}</p>}
    </div>
  );
}
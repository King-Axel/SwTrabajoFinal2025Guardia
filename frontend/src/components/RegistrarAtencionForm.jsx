import React from "react";

const RegistrarAtencionForm = () => {
  return (
    <form id="root" className="form">
      <div className="card">
        <div className=" form-block flex items-center gap-2 justify-center mb-4">
          {/* <ClipboardList className="form-icon" /> */}
          <h2 className="text-xl font-semibold">
            Registrar Informe de Atención
          </h2>
        </div>

        <div className="form-block">
          <label>Paciente *</label>
          <select className="input">
            <option value="">Seleccionar paciente</option>
            <option value="1">Paciente 1</option>
            <option value="2">Paciente 2</option>
          </select>
        </div>

        <div className="form-block">
          <label>Ingreso *</label>
          <input
            type="text"
            className="input"
            placeholder="Número de ingreso"
          />
        </div>

        <div className="form-block">
          <label>Médico *</label>
          <input
            type="text"
            className="input"
            placeholder="Nombre del médico"
          />
        </div>

        <div className="form-block">
          <label>Informe de Atención *</label>
          <textarea
            className="input"
            placeholder="Descripción detallada de la atención brindada..."
          ></textarea>
        </div>

        <button className="button botonRegistrar">Registrar Atención</button>
      </div>
    </form>
  );
};

export default RegistrarAtencionForm;

import IngresoUrgenciasForm from "../components/IngresoUrgenciasForm";

export default function NuevaAdmision() {
  return (
    <div>
      <h2 className="text-2xl font-bold mb-3">Registro de Admisión</h2>
      <p className="text-gray-500 mb-5">
        Complete el formulario con los datos vitales y la clasificación de triaje.
      </p>

      <div className="bg-white shadow rounded-xl p-6">
        <IngresoUrgenciasForm />
      </div>
    </div>
  );
}

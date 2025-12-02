import RegistrarPacienteForm from "../components/RegistrarPacienteForm";

export default function RegistrarPaciente() {
  return (
    <div>
      <h2 className="text-2xl font-bold mb-3">Registro de Paciente</h2>
      <p className="text-gray-500 mb-5">Complete los datos del paciente.</p>

      <div className="bg-white shadow rounded-xl p-6">
        <RegistrarPacienteForm />
      </div>
    </div>
  );
}

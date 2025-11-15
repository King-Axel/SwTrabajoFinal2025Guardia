import React, { useState, useMemo, useRef, useEffect } from "react"
import { useNavigate } from 'react-router-dom';


const RegisterForm = ({ onSubmit } = {}) => {
  const [email, setEmail] = useState("");
  const [contrasena, setContrasena] = useState("");
  const [mostrarContra, setMostrarContra] = useState("");
  const [rol, setRol] = useState(null);
  const [personal, setPersonal] = useState("");
  const [buscar, setBuscar] = useState("");
  const [errors, setErrors] = useState({});
  const [abierto, setAbierto] = useState(false);
  const containerRef = useRef(null);
  const navigate = useNavigate();

  const filtrados = useMemo(() => {
    const opciones = [
      { rol: "ENFERMERA", nombre: "Lopez, Jacinta Maria" },
      { rol: "ENFERMERA", nombre: "Pérez, Ana Lucía" },
      { rol: "MEDICO", nombre: "Gomez, Carlos Alberto" },
      { rol: "MEDICO", nombre: "Rivas, Julia" }
    ]

    if (!rol) return [];

    const q = buscar.trim().toLowerCase();

    return opciones
      .filter((p) => p.rol === rol)
      .filter((p) => (q === "" ? true : p.nombre.toLowerCase().includes(q)));
  }, [rol, buscar]);

  useEffect(() => {
    function handlePointerDown(e) {
      if (containerRef.current && !containerRef.current.contains(e.target)) {
        setAbierto(false);
      }
    }

    document.addEventListener("pointerdown", handlePointerDown);
    return () => document.removeEventListener("pointerdown", handlePointerDown);
  }, []);

  const handleSubmit = (e) => {
    e.preventDefault();

    const newErrors = {};
    if (!rol) newErrors.rol = "Seleccione un rol";
    if (!personal) newErrors.personal = "Seleccione el personal asociado";
    setErrors(newErrors);

    const payload = { mode: 'login', email, contrasena };
    if (onSubmit && Object.keys(newErrors).length == 0) onSubmit(payload);
    navigate('/');
  }

  const onSelectPersonal = (p) => {
    setPersonal(p.nombre);
    setRol(p.rol);
    setBuscar(p.nombre);
    setAbierto(false);
    setErrors((prev) => { ({ ...prev, personal: undefined }) });
  }

  const onChangeRol = (newRol) => {
    setRol(newRol);
    setPersonal("");
    setBuscar("");
    setErrors((prev) => { ({ ...prev, rol: undefined, personal: undefined }) });
  }

  return (
    <>
      <form className="xl:flex xl:flex-col lg:p-0 form p-5" onSubmit={handleSubmit}>
        <div className='form-block'>
          <label htmlFor="email">
            Correo Electrónico
          </label>
          <div className='relative'>
            <i className='bi bi-envelope form-icon absolute left-2'></i>
            <input
              type="email"
              id="email"
              value={email}
              placeholder='usuario@ejemplo.com'
              onChange={(e) => setEmail(e.target.value)}
              className='input px-3'
            />
          </div>
        </div>
        <div className='form-block relative'>
          <label htmlFor="contrasena">
            Contraseña
          </label>
          <div className='relative'>
            <i className={`bi form-icon ${mostrarContra ? 'bi-unlock' : 'bi-lock'} absolute left-2`}></i>
            <input
              type={mostrarContra ? "text" : "password"}
              value={contrasena}
              id="contrasena"
              placeholder='ingrese su contraseña'
              onChange={(e) => setContrasena(e.target.value)}
              className='input'
            />
            <button
              type='button'
              onClick={() => setMostrarContra((prev) => !prev)}
              className='absolute right-2 top-1/2 -translate-y-3'
            >
              <i className={`bi form-icon ${mostrarContra ? 'bi-eye-slash' : 'bi-eye'}`}></i>
            </button>
          </div>
        </div>
        <div className="form-block">
          <label htmlFor="">
            Seleccione su rol
          </label>
          <div className="form-block-radio-options">
            <label htmlFor="enfermera" className="form-block-radio">
              <span>Enfermera</span>
              <input
                className="cursor-pointer"
                type="radio" id="enfermera" name="rol" value="ENFERMERA"
                onChange={() => { onChangeRol('ENFERMERA') }}
              />
            </label>
            <label htmlFor="medico" className="form-block-radio">
              <span>Médico</span>
              <input
                className="cursor-pointer"
                type="radio" id="medico" name="rol" value="MEDICO"
                onChange={() => { onChangeRol('MEDICO') }}
              />
            </label>
          </div>
          {/*errors.rol && <p className="text-red-600 text-sm mt-1">{errors.rol}</p>*/}
        </div>
        <div className="form-block relative" ref={containerRef}>
          <label htmlFor="personal">Personal asociado</label>

          <div className="relative">
            <i className='bi bi-person form-icon absolute left-2'></i>

            <input
              type="text"
              name="personal"
              id="personal"
              placeholder={rol ? "Buscar personal..." : 'Primero seleccione un rol'}
              className="input"
              value={personal}
              onChange={(e) => { setPersonal(e.target.value); setBuscar(e.target.value); setAbierto(true); }}
              onFocus={() => { if (rol) setAbierto(true); }}
              disabled={!rol}
            />
          </div>

          <ul className={`${abierto ? "" : "hidden"} absolute bg-white overflow-y-scroll scroll-0 border border-gray-300 max-h-[200px] w-full no-scrollbar`}>
            {
              filtrados.length === 0? (
                <li className="px-10 py-2 text-gray-500 border-t border-gray-300">No se encontraron resultados</li>
              ) : (
                filtrados.map(p => (
                  <li
                    key={p.nombre}
                    className="px-10 py-2 hover:bg-gray-200 cursor-pointer border-t border-gray-300"
                    onMouseDown={(e) => { e.preventDefault(); onSelectPersonal(p); }}
                  ><span className="font-bold">{p.rol}</span> - {p.nombre}</li>
                ))
              )}
          </ul>
        </div>

        <button
          type='submit'
          className='xl:absolute bottom-0 lg:mt-4 button'
        >
          Registrarme
        </button>

      </form>
    </>
  )
}

export default RegisterForm;
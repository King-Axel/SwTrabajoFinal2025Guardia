export const saveToken = (token) => localStorage.setItem('token', token);
export const clearToken = () => localStorage.removeItem('token');
export const isAuthenticated = () => !!getToken();

export function getToken() {
  const t = localStorage.getItem("token");
  if (!t) return null;

  try {
    const payload = JSON.parse(atob(t.split(".")[1]));
    const now = Math.floor(Date.now() / 1000);
    if (payload.exp && payload.exp <= now) {
      localStorage.removeItem("token");
      return null;
    }
  } catch {
    // si no se puede parsear, lo limpiamos
    localStorage.removeItem("token");
    return null;
  }

  return t;
}

export function getUserRole() {
  const t = getToken();
  if (!t) return null;

  try {
    const payload = JSON.parse(atob(t.split(".")[1]));
    const rol = payload?.rol;

    if (typeof rol === "string") return rol;      // "MEDICO" / "ENFERMERA"
    if (rol?.name) return rol.name;               // si viene como objeto
    if (rol?.authority) return rol.authority;     // si viene "ROLE_MEDICO"
    return null;
  } catch {
    return null;
  }
}
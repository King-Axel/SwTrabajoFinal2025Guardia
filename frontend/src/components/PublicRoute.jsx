import { Navigate } from "react-router-dom";
import { getToken } from "../utils/auth";

export default function PublicRoute({ children }) {
  const token = getToken();
  if (token) return <Navigate to="/urgencias" replace />;
  return children;
}
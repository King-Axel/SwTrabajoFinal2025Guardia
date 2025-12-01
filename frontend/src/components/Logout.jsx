import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { clearToken } from "../utils/auth";

export default function Logout() {
  const navigate = useNavigate();

  useEffect(() => {
    clearToken();
    navigate("/login", { replace: true });
  }, []);


  return null;
}
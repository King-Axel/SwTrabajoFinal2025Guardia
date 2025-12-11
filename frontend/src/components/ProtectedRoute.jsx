import React from "react";
import { Navigate } from "react-router-dom";
import { getToken, getUserRole } from "../utils/auth";

function normalizeRole(r) {
  if (!r) return null;
  return String(r).toUpperCase().replace(/^ROLE_/, "");
}

export default function ProtectedRoute({ children, allowedRoles }) {
  const token = getToken();
  if (!token) return <Navigate to="/login" replace />;

  if (allowedRoles && Array.isArray(allowedRoles) && allowedRoles.length > 0) {
    const userRole = normalizeRole(getUserRole());
    const allowed = allowedRoles
      .map(normalizeRole)
      .filter(Boolean);

    if (!userRole || !allowed.includes(userRole)) {
      return <Navigate to="/urgencias" replace />;
    }
  }

  return children;
}
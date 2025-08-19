import React from 'react';
import { Navigate } from 'react-router-dom';

const AdminRoute = ({ children }) => {
  const token = localStorage.getItem('token');
  if (!token) return <Navigate to="/users/signin" />;

  const role = JSON.parse(atob(token.split('.')[1])).role;
  return role === 'ROLE_ADMIN' ? children : <Navigate to="/" />;
};

export default AdminRoute;

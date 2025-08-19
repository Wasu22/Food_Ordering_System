import axios from 'axios';

const BASE_URL = 'http://localhost:8080'; 

 export const signup = async (userData) => {
  try {
    const response = await axios.post(`${BASE_URL}/users/signup`, userData);
  return response.data;
  } catch (err) {
    
    throw err;
  }
};

export const login = async (credentials) => {
  const response = await axios.post(`${BASE_URL}/users/signin`, credentials);
  const { jwt, customerId, message } = response.data;

  localStorage.setItem('token', jwt);
  localStorage.setItem('userId', customerId);
  return { jwt, customerId, message };
};

export const logout = () => {
  localStorage.removeItem('token');
  localStorage.removeItem('userId');
};

export const getToken = () => localStorage.getItem('token');

export const getRole = () => {
  const token = localStorage.getItem("token");
  if (!token) return null;

  const payload = JSON.parse(atob(token.split(".")[1]));
  return payload.role || null;
};



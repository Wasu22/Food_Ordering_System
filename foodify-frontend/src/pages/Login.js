import React, { useState } from 'react';
import { login } from '../services/authService';
import { useNavigate } from 'react-router-dom';

const Login = () => {
  const [credentials, setCredentials] = useState({ email: '', password: '' });
  const navigate = useNavigate();

  const handleChange = (e) => {
    setCredentials({ ...credentials, [e.target.name]: e.target.value });
  };

  const handleLogin = async (e) => {
  e.preventDefault();
  try {
    const response = await login(credentials); 

    localStorage.setItem('token', response.jwt);

    const decodedPayload = JSON.parse(atob(response.jwt.split('.')[1]));
    localStorage.setItem('userRole', decodedPayload.role);  
    localStorage.setItem('userEmail', decodedPayload.sub);  
    alert(response.message);
    navigate('/');
  } catch (err) {
    alert('Login failed: ' + err.response?.data?.message || 'Server error');
  }
};


  const goToSignup = () => {
    navigate('/users/signup');
  };

  return (
    <div className="container mt-4">
      <h2>Login</h2>
      <form onSubmit={handleLogin}>
        <input
          type="email"
          name="email"
          placeholder="Email"
          className="form-control my-2"
          onChange={handleChange}
          required
        />
        <input
          type="password"
          name="password"
          placeholder="Password"
          className="form-control my-2"
          onChange={handleChange}
          required
        />
        <div className="d-flex gap-3">
          <button type="submit" className="btn btn-primary">
            Login
          </button>
          <button type="button" className="btn btn-secondary" onClick={goToSignup}>
            Sign Up
          </button>
        </div>
      </form>
      <br /><br /><br /><br />
    </div>
  );
};

export default Login;

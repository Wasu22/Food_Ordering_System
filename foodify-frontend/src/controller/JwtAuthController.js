import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const SignUp = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    dob: '',
    userRole: 'CUSTOMER'
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await axios.post('http://localhost:8080/users/signup', formData);
      alert(res.data.message);
      navigate('/login');
    } catch (err) {
      alert(err.response?.data?.message || 'SignUp failed');
    }
  };

  return (
    <div className="container mt-5">
      <h2>Sign Up</h2>
      <form onSubmit={handleSubmit}>
        <input type="text" name="firstName" placeholder="First Name" onChange={handleChange} className="form-control mb-2" required />
        <input type="text" name="lastName" placeholder="Last Name" onChange={handleChange} className="form-control mb-2" required />
        <input type="email" name="email" placeholder="Email" onChange={handleChange} className="form-control mb-2" required />
        <input type="password" name="password" placeholder="Password" onChange={handleChange} className="form-control mb-2" required />
        <input type="date" name="dob" onChange={handleChange} className="form-control mb-2" required />
        <select name="userRole" onChange={handleChange} className="form-control mb-3">
          <option value="CUSTOMER">Customer</option>
          <option value="ADMIN">Admin</option>
          <option value="DELIVERYPERSON">Delivery Person</option>
        </select>
        <button type="submit" className="btn btn-primary">Register</button>
      </form>
    </div>
  );
};

const Login = () => {
  const navigate = useNavigate();
  const [credentials, setCredentials] = useState({ email: '', password: '' });

  const handleChange = (e) => {
    setCredentials({ ...credentials, [e.target.name]: e.target.value });
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const res = await axios.post('http://localhost:8080/users/signin', credentials);
      localStorage.setItem('token', res.data.jwt);
      localStorage.setItem('userId', res.data.customerId);
      alert(res.data.message);
      navigate('/');
    } catch (err) {
      alert(err.response?.data?.message || 'Login failed');
    }
  };

  return (
    <div className="container mt-5">
      <h2>Sign In</h2>
      <form onSubmit={handleLogin}>
        <input type="email" name="email" placeholder="Email" onChange={handleChange} className="form-control mb-3" required />
        <input type="password" name="password" placeholder="Password" onChange={handleChange} className="form-control mb-3" required />
        <button type="submit" className="btn btn-primary">Login</button>
      </form>
    </div>
  );
};

export { SignUp, Login };

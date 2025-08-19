// src/pages/SignUp.js
import React, { useState } from 'react';
import { signup } from '../services/authService';
import { useNavigate } from 'react-router-dom';

const SignUp = () => {
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    dob: '',
    email: '',
    password: '',
    userRole: 'CUSTOMER'
  });

  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await signup(formData);
      alert('Signup successful!');
      //navigate('/login');
      navigate('/users/signin');
    } catch (err) {
      alert('Signup failed: ' + err.response?.data?.message);
    }
  };

  return (
    <div className="container mt-4">
      <h2>Sign Up</h2>
      <form onSubmit={handleSubmit}>
        <input name="firstName" placeholder="First Name" className="form-control my-2" onChange={handleChange} required />
        <input name="lastName" placeholder="Last Name" className="form-control my-2" onChange={handleChange} required />
        <input name="dob" type="date" className="form-control my-2" onChange={handleChange} required />
        <input name="email" type="email" placeholder="Email" className="form-control my-2" onChange={handleChange} required />
        <input name="password" type="password" placeholder="Password" className="form-control my-2" onChange={handleChange} required />
        <select name="userRole" className="form-control my-2" onChange={handleChange} required>
          <option value="CUSTOMER">Customer</option>
          <option value="ADMIN">Admin</option>
          <option value="DELIVERYPERSON">Delivery Person</option>
        </select>
        <button type="submit" className="btn btn-primary">Register</button>
      </form>
      <br/>
    </div>
  );
};

export default SignUp;

import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './MainNavBar.css';

const MainNavBar = () => {
  const navigate = useNavigate();
  const token = localStorage.getItem('token');

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('userRole'); 
    navigate('/'); 
  };

  return (
    <nav className="navbar">
      <div className="logo"><i>Foodify</i></div>

      <ul className="nav-links">
        <li><Link to="/">Home</Link></li>
        <li><Link to="/restaurants">Restaurants</Link></li>
        <li><Link to="/about">About</Link></li>
        <li><Link to="/contact">Contact</Link></li>
      </ul>

      <div className="search-bar">
        <input type="text" placeholder="Search here" />
        <button type="submit">Search</button>
      </div>

      <div className="auth-buttons">
        {token ? (
          <button onClick={handleLogout} className="logout-btn">Logout</button>
        ) : (
          <>
            <Link to="/users/signin" className="login-link">Login</Link>
            <Link to="/users/signup" className="signup-btn">Sign Up</Link>
          </>
        )}
      </div>
    </nav>
  );
};

export default MainNavBar;

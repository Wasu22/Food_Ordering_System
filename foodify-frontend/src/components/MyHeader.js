import React from 'react';
import './MyHeader.css';

const Header = () => {
  return (
    <header className="header">
      <div className="overlay">
        <h1>Hungry?!</h1>
        <p>Don't wait!!! <br/> Let's start to order food now!</p>
        <div className="header-buttons">
          <button className="btn-order">Order Now</button>
          <button className="btn-menu">View Food items</button>
        </div>
      </div>
    </header>
  );
};

export default Header;

import React from 'react';
import './MyFooter.css';

const Footer = () => {
  return (
    <footer className="footer">
      <div className="footer-content">
        <div className="footer-section about">
          <h3 className="brand-name"><i>Foodify</i></h3>
          <div className="social-icons">
            <i className="fab fa-twitter"></i>
            <i className="fab fa-facebook"></i>
            <i className="fab fa-instagram"></i>
          </div>
          <p>
            Passionate about delivering unforgettable pizza experiences with every savory slice we create.
          </p>
        </div>

        <div className="footer-section">
          <h5>OPENING HOURS</h5>
          <p>Monday - Friday<br />8:00am - 9:00pm</p>
        </div>

        <div className="footer-section">
          <h5>SERVICES</h5>
          <p>
            Dine-In<br />
            Online Ordering<br />
            Catering<br />
            Specialty Pizzas
          </p>
        </div>

        <div className="footer-section contact">
          <h5>HAVE A QUESTION?</h5>
          <p>
            <i className="fas fa-map-marker-alt"></i>20 Graham Rd,<br /> Malvern WR14 2HL,<br /> India <br />
            <i className="fas fa-phone"></i> +91 1684892229<br />
            <i className="fas fa-envelope"></i> info@Foodify.com
          </p>
        </div>
      </div>
      <div className="footer-bottom">© 2025 Foodify. All rights reserved.</div>
    </footer>
  );
};

export default Footer;

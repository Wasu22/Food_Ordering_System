import React from 'react';
import './ContactUs.css';

const ContactUs = () => {
  return (
    <div className="contact-page">
      <div className="contact-info">
        <div className="info-box">
          <i className="fas fa-map-marker-alt"></i>
          <h4>OUR MAIN OFFICE</h4>
          <p>20 Graham Rd,Malvern WR14 2HL,India</p>
        </div>
        <div className="info-box">
          <i className="fas fa-phone"></i>
          <h4>PHONE NUMBER</h4>
          <p>+91 1684892229<br />+91 2464892229 (Toll Free)</p>
        </div>
        <div className="info-box">
          <i className="fas fa-fax"></i>
          <h4>FAX</h4>
          <p>1-234-567-8900</p>
        </div>
        <div className="info-box">
          <i className="fas fa-envelope"></i>
          <h4>EMAIL</h4>
          <p><a href="info@Foodify.com">info@Foodify.com</a></p>
        </div>
      </div>

      <div className="contact-form-box">
        <h2>Contact Us</h2>
        <form className="contact-form">
          <input type="text" placeholder="Enter your Name" required />
          <input type="email" placeholder="Enter a valid email address" required />
          <textarea placeholder="Enter your message" rows="5" required></textarea>
          <button type="submit">SUBMIT</button>
        </form>
      </div>
    </div>
  );
};

export default ContactUs;

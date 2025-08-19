import React from 'react';
import './About.css'; 
const teamMembers = [
  {
    name: 'John Doe',
    role: 'Founder & CEO',
    image: 'https://www.sevensquaretech.com/wp-content/uploads/2024/10/New-Krutarth-Shah.webp',
  },
  {
    name: 'Jane Smith',
    role: 'CTO',
    image: 'https://www.sevensquaretech.com/wp-content/uploads/2024/10/New-Atit-Purani.webp',
  },
  {
    name: 'Alex Johnson',
    role: 'Head of Marketing',
    image: 'https://www.sevensquaretech.com/wp-content/uploads/2024/10/New-Payal-Sheth.webp',
  },
  {
    name: 'Maria Williams',
    role: 'Lead Designer',
    image: 'https://www.sevensquaretech.com/wp-content/uploads/2025/04/avtar-about.png',
  },
];

const About = () => {
  return (
    <div className="about-container">
      {/* Hero Section */}
      <div className="about-hero">
        <h1>About Foodify</h1>
        <p>Your favorite food, delivered fast at your door.</p>
      </div>

      {/* Company Info */}
      <div className="about-content">
        <h2>Who We Are</h2>
        <p>
          Foodify is dedicated to making your food ordering experience fast, simple, and reliable. We partner with hundreds of restaurants to bring diverse cuisines right to your doorstep. Whether you crave spicy, sweet, or healthy meals — Foodify has you covered.
        </p>

        <h2>Our Mission</h2>
        <p>
          We aim to revolutionize online food delivery with exceptional service, innovative technology, and a passion for customer satisfaction.
        </p>

        {/* Team Section */}
        <h2>Meet Our Team</h2>
        <div className="team-grid">
          {teamMembers.map((member, index) => (
            <div key={index} className="team-card">
              <img src={member.image} alt={member.name} />
              <h3>{member.name}</h3>
              <p>{member.role}</p>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default About;
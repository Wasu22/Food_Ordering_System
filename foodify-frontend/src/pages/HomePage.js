import React from 'react';
import Header from '../components/MyHeader';
import Footer from '../components/MyFooter';
import './Home.css';

const Home = () => {
  return (
    <>
      <Header />
      <main className="main-content">
        <section className="services">
          <h2>OUR SERVICES</h2>
          <div className="service-items">
            <div>
              <h4>HEALTHY FOOD</h4>
              <p>Fresh ingredients <br/>Balanced nutrition<br/> Pure satisfaction </p>
            </div>
            <div>
              <h4>FAST DELIVERY</h4>
              <p>On-time and safe delivery with real-time tracking</p>
            </div>
            <div>
              <h4>ORIGINAL RECIPES</h4>
              <p>Tradition you can taste, authenticity you can trust</p>
            </div>
          </div>
        </section>

        <section className="hot-meals">
          <h2>SPECIALITIES</h2>
          <div className="meals-grid">
            <div className="meal-card">
              <img src="\Desert.jpg" alt="Desserts" />
              <h5>Desserts</h5>
              <p>Sweet endings crafted to melt hearts — from classic delights to indulgent surprises</p>
              <button>Explore</button>
            </div>
            <div className="meal-card">
              <img src="\NorthIndian.jpg" alt="NORTH INDIAN" />
              <h5>North Indian</h5>
              <p>Rich gravies, bold spices, and comforting warmth straight from the heart of India</p>
              <button>Explore</button>
            </div>
            <div className="meal-card">
              <img src="\SouthIndian.jpg" alt="South Indian" />
              <h5>South Indian</h5>
              <p>Aromatic, vibrant, and soulful — flavors born from coconut, curry leaves, and tradition</p>
              <button>Explore</button>
            </div>
            <div className="meal-card">
              <img src="\Biryani.jpg" alt="Biryani" />
              <h5>Biryani</h5>
              <p>Fragrant rice layered with tender meat and ancient spice blends — a royal feast in every bite</p>
              <button>Explore</button>
            </div>
          </div>
        </section>

        <section className="contact-section">
          <h2>CONTACT US</h2>
          <form className="contact-form">
            <input type="text" placeholder="First Name" required />
            <input type="text" placeholder="Last Name" required />
            <input type="email" placeholder="Email" required />
            <input type="tel" placeholder="Phone Number" required />
            <textarea placeholder="Enter your inquiry..." required></textarea>
            <button type="submit">Send Inquiry</button>
          </form>
        </section>
      </main>
      {/* <Footer /> */}
    </>
  );
};

export default Home;

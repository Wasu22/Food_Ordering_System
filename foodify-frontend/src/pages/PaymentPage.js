// src/pages/PaymentPage.js
import React, { useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";

const PaymentPage = () => {
  const { orderId } = useParams();
  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  const [paymentDetails, setPaymentDetails] = useState({
    cardNumber: "",
    expiry: "",
    cvv: "",
  });

  const handleChange = (e) => {
    setPaymentDetails({ ...paymentDetails, [e.target.name]: e.target.value });
  };

  const handlePayment = async (e) => {
    e.preventDefault();

    try {
      // Call backend to mark payment as completed
      await axios.post(
        `http://localhost:8080/payments/${orderId}`,
        paymentDetails,
        { headers: { Authorization: `Bearer ${token}` } }
      );

      alert("Payment Successful!");
      navigate(`/order-success/${orderId}`);
    } catch (error) {
      console.error("Payment error:", error);
      alert("Payment failed. Please try again.");
    }
  };

  return (
    <div style={{ padding: "20px" }}>
      <h2>Payment</h2>
      <form onSubmit={handlePayment}>
        <div>
          <label>Card Number:</label>
          <input
            type="text"
            name="cardNumber"
            value={paymentDetails.cardNumber}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label>Expiry Date:</label>
          <input
            type="text"
            name="expiry"
            value={paymentDetails.expiry}
            onChange={handleChange}
            placeholder="MM/YY"
            required
          />
        </div>
        <div>
          <label>CVV:</label>
          <input
            type="password"
            name="cvv"
            value={paymentDetails.cvv}
            onChange={handleChange}
            required
          />
        </div>
        <button
          type="submit"
          style={{
            backgroundColor: "blue",
            color: "white",
            padding: "10px 20px",
            border: "none",
            cursor: "pointer",
            marginTop: "20px",
          }}
        >
          Pay Now
        </button>
      </form>
    </div>
  );
};

export default PaymentPage;

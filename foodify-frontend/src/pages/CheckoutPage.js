import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";

const CheckoutPage = () => {
  const { orderId } = useParams();
  const [cartItems, setCartItems] = useState([]);
  const [subtotal, setSubtotal] = useState(0);
  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  useEffect(() => {
    const fetchCartItems = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/carts/order/${orderId}`,
          { headers: { Authorization: `Bearer ${token}` } }
        );
        setCartItems(response.data);

        // Calculate subtotal
        const total = response.data.reduce(
          (sum, item) => sum + (item.foodItem?.price || 0) * item.quantity,
          0
        );
        setSubtotal(total);
      } catch (error) {
        console.error("Error fetching cart items:", error);
      }
    };

    fetchCartItems();
  }, [orderId, token]);

  return (
    <div style={{ padding: "20px" }}>
      <h2>Checkout</h2>
      {cartItems.length === 0 ? (
        <p>Your cart is empty!</p>
      ) : (
        <>
          <ul>
            {cartItems.map((ci) => (
              <li key={ci.id}>
                {ci.foodItem?.foodName} - ₹{ci.foodItem?.price} × {ci.quantity}
              </li>
            ))}
          </ul>

          <h3>Subtotal: ₹{subtotal}</h3>

          <button
            style={{
              backgroundColor: "green",
              color: "white",
              padding: "10px 20px",
              border: "none",
              cursor: "pointer",
              marginTop: "20px",
            }}
            onClick={() => navigate(`/payment/${orderId}`)}
          >
            Proceed to Payment
          </button>
        </>
      )}
    </div>
  );
};

export default CheckoutPage;

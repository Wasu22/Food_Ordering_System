import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";

const PlaceOrderPage = () => {
  const { restaurantId } = useParams();
  const [restaurantData, setRestaurantData] = useState(null);
  const [cartItems, setCartItems] = useState([]);
  const [orderId, setOrderId] = useState(null);
  const [orderTotal, setOrderTotal] = useState(0);

  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  // Fetch restaurant menu
  useEffect(() => {
    const fetchRestaurantMenu = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/restaurants/getrestaurant/${restaurantId}/food_items`,
          { headers: { Authorization: `Bearer ${token}` } }
        );
        setRestaurantData(response.data);
      } catch (error) {
        console.error("Error fetching restaurant menu:", error);
      }
    };
    fetchRestaurantMenu();
  }, [restaurantId, token]);

  // Fetch cart items for the current order
  const fetchCartItems = async (orderIdParam) => {
    try {
      const response = await axios.get(
        `http://localhost:8080/carts/order/${orderIdParam}`,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setCartItems(response.data);
    } catch (error) {
      console.error("Error fetching cart items:", error);
    }
  };

  // Fetch order details
  const fetchOrderDetails = async (orderIdParam) => {
    try {
      const response = await axios.get(
        `http://localhost:8080/orders/${orderIdParam}`,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setOrderTotal(response.data.totalAmount);
    } catch (error) {
      console.error("Error fetching order details:", error);
    }
  };

  // Create order
  const handleCreateOrder = async () => {
    try {
      const dto = {
        customerId: 1, // Replace with actual logged-in user id
        restaurantId: restaurantId,
        orderStatus: "NEW",
        deliveryCharges: 40,
      };

      const response = await axios.post(
        "http://localhost:8080/orders",
        dto,
        { headers: { Authorization: `Bearer ${token}` } }
      );

      const newOrderId = response.data.id;
      setOrderId(newOrderId);
      fetchCartItems(newOrderId);

      // Navigate directly to checkout
      navigate(`/checkout/${newOrderId}`);
    } catch (error) {
      console.error("Error creating order:", error);
      alert("Failed to create order");
    }
  };

  // Add item to cart
  const handleAddToCart = async (foodItemId) => {
    try {
      const cartItem = { quantity: 1 };
      await axios.post(
        `http://localhost:8080/carts/add/${foodItemId}`,
        cartItem,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      fetchCartItems(orderId);
    } catch (error) {
      console.error("Error adding to cart:", error);
      alert("Failed to add item to cart");
    }
  };

  // Remove item from cart
  const handleRemoveFromCart = async (cartItemId) => {
    try {
      await axios.delete(
        `http://localhost:8080/carts/remove/${cartItemId}`,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      fetchCartItems(orderId);
    } catch (error) {
      console.error("Error removing from cart:", error);
      alert("Failed to remove item from cart");
    }
  };

  // Update quantity
  const updateQty = async (cartItemId, qty) => {
    try {
      await axios.put(
        `http://localhost:8080/carts/update/${cartItemId}`,
        { quantity: qty },
        { headers: { Authorization: `Bearer ${token}` } }
      );
      fetchCartItems(orderId);
      fetchOrderDetails(orderId); // refresh total
    } catch (error) {
      console.error("Error updating quantity:", error);
    }
  };

  const increaseQty = (cartItemId) => {
    const item = cartItems.find((ci) => ci.id === cartItemId);
    if (!item) return;
    updateQty(cartItemId, item.quantity + 1);
  };

  const decreaseQty = (cartItemId) => {
    const item = cartItems.find((ci) => ci.id === cartItemId);
    if (!item || item.quantity <= 1) return;
    updateQty(cartItemId, item.quantity - 1);
  };

  // Calculate subtotal
  const subtotal = cartItems.reduce(
    (total, ci) => total + (ci.foodItem?.price || 0) * ci.quantity,
    0
  );

  const isItemInCart = (foodItemId) =>
    cartItems.some((item) => item.foodItem?.id === foodItemId);

  return (
    <div className="menu-page">
      <h2>Restaurant Menu</h2>

      {!restaurantData ? (
        <p>Loading menu...</p>
      ) : (
        <>
          <h3>{restaurantData.name}</h3>

          {!orderId && (
            <button onClick={handleCreateOrder}>Place Order</button>
          )}

          <div className="food-list">
            {restaurantData.foodItems?.map((item) => {
              const inCart = isItemInCart(item.id);
              const cartItem = cartItems.find(
                (ci) => ci.foodItem?.id === item.id
              );

              return (
                <div key={item.id} className="food-card">
                  <h4>{item.itemName}</h4>
                  <p>{item.itemDescription}</p>
                  <p>Price: ₹{item.price}</p>

                  {inCart ? (
                    <button
                      onClick={() => handleRemoveFromCart(cartItem.id)}
                      style={{ backgroundColor: "red", color: "white" }}
                    >
                      Remove from Cart
                    </button>
                  ) : (
                    orderId && (
                      <button
                        onClick={() => handleAddToCart(item.id)}
                        style={{ backgroundColor: "green", color: "white" }}
                      >
                        Add to Cart
                      </button>
                    )
                  )}
                </div>
              );
            })}
          </div>

          {cartItems.length > 0 && (
            <div className="cart-summary" style={{ marginTop: "30px" }}>
              <h3>My Cart</h3>

              {cartItems.map((ci) => (
                <div
                  key={ci.id}
                  style={{
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "space-between",
                    borderBottom: "1px solid #ccc",
                    padding: "10px 0",
                  }}
                >
                  <span>{ci.foodItem?.name}</span>
                  <div style={{ display: "flex", alignItems: "center" }}>
                    <button onClick={() => decreaseQty(ci.id)}>-</button>
                    <span style={{ margin: "0 10px" }}>{ci.quantity}</span>
                    <button onClick={() => increaseQty(ci.id)}>+</button>
                  </div>
                  <span>₹{ci.foodItem?.price}</span>
                  <button
                    style={{
                      color: "red",
                      border: "none",
                      background: "none",
                    }}
                    onClick={() => handleRemoveFromCart(ci.id)}
                  >
                    ✖
                  </button>
                </div>
              ))}

              <div
                style={{
                  marginTop: "20px",
                  display: "flex",
                  justifyContent: "space-between",
                }}
              >
                <strong>Subtotal:</strong>
                <strong>₹{subtotal}</strong>
              </div>
            </div>
          )}
        </>
      )}
    </div>
  );
};

export default PlaceOrderPage;

// import React, { useEffect, useState } from "react";
// import { useParams, useNavigate } from "react-router-dom";
// import axios from "axios";

// const PlaceOrderPage = () => {
//   const { restaurantId } = useParams();
//   const [cartItems, setCartItems] = useState([]);
//   const [orderId, setOrderId] = useState(null);
//   const [orderTotal, setOrderTotal] = useState(0);

//   const navigate = useNavigate();
//   const token = localStorage.getItem("token");

//   // Fetch existing orderId for user (or create new if none)
//   useEffect(() => {
//     const fetchOrCreateOrder = async () => {
//       try {
//         // Try to get latest order
//         const orderRes = await axios.get(
//           `http://localhost:8080/orders/${restaurantId}`,
//           { headers: { Authorization: `Bearer ${token}` } }
//         );

//         if (orderRes.data?.id) {
//           setOrderId(orderRes.data.id);
//           fetchCartItems(orderRes.data.id);
//           fetchOrderDetails(orderRes.data.id);
//         } else {
//           // Create new order if none
//           const dto = {
//             customerId: 1, // replace with logged-in user id
//             restaurantId,
//             orderStatus: "NEW",
//             deliveryCharges: 40,
//           };
//           const newOrder = await axios.post(
//             "http://localhost:8080/orders",
//             dto,
//             { headers: { Authorization: `Bearer ${token}` } }
//           );
//           setOrderId(newOrder.data.id);
//         }
//       } catch (err) {
//         console.error("Error fetching/creating order:", err);
//       }
//     };
//     fetchOrCreateOrder();
//   }, [restaurantId, token]);

//   // Fetch cart items
//   // const fetchCartItems = async (orderIdParam) => {
//   //   try {
//   //     const res = await axios.get(
//   //       `http://localhost:8080/carts/order/${orderIdParam}`,
//   //       { headers: { Authorization: `Bearer ${token}` } }
//   //     );
//   //     setCartItems(res.data);
//   //   } catch (err) {
//   //     console.error("Error fetching cart items:", err);
//   //   }
//   // };

// // useEffect(() => {
// //   const fetchCartItems = async () => {
// //     const userId = localStorage.getItem("userId");
// //     const token = localStorage.getItem("token");

// //     try {
// //       const response = await fetch(`http://localhost:8080/carts/user/${userId}`, {
// //         method: "GET",
// //         headers: {
// //           "Authorization": `Bearer ${token}`,
// //           "Content-Type": "application/json"
// //         }
// //       });

// //       if (!response.ok) throw new Error("Failed to fetch cart items");

// //       const data = await response.json();
// //       setCartItems(data); // Now cartItems will have quantity, price, foodItemName
// //     } catch (error) {
// //       console.error(error);
// //     }
// //   };

// //   fetchCartItems();
// // }, []);

// // Fetch cart items by userId
// const fetchCartItems = async () => {
//   try {
//     const userId = localStorage.getItem("userId");
//     const token = localStorage.getItem("token");

//     const res = await axios.get(
//       `http://localhost:8080/carts/user/${userId}`,
//       { headers: { Authorization: `Bearer ${token}` } }
//     );

//     setCartItems(res.data);
//   } catch (err) {
//     console.error("Error fetching cart items:", err);
//   }
// };



//   // Fetch order details (for total)
//   const fetchOrderDetails = async (orderIdParam) => {
//     try {
//       const res = await axios.get(
//         `http://localhost:8080/orders/${orderIdParam}`,
//         { headers: { Authorization: `Bearer ${token}` } }
//       );
//       setOrderTotal(res.data.totalAmount);
//     } catch (err) {
//       console.error("Error fetching order details:", err);
//     }
//   };

//   // Increase quantity
//   const increaseQty = async (cartItemId) => {
//     const item = cartItems.find((ci) => ci.id === cartItemId);
//     if (!item) return;
//     updateQty(cartItemId, item.quantity + 1);
//   };

//   // Decrease quantity
//   const decreaseQty = async (cartItemId) => {
//     const item = cartItems.find((ci) => ci.id === cartItemId);
//     if (!item || item.quantity <= 1) return;
//     updateQty(cartItemId, item.quantity - 1);
//   };

//   // Update quantity in backend
//   const updateQty = async (cartItemId, qty) => {
//     try {
//       await axios.put(
//         `http://localhost:8080/carts/update/${cartItemId}`,
//         { quantity: qty },
//         { headers: { Authorization: `Bearer ${token}` } }
//       );
//       fetchCartItems(orderId);
//       fetchOrderDetails(orderId);
//     } catch (err) {
//       console.error("Error updating quantity:", err);
//     }
//   };

//   // Remove from cart
//   const handleRemoveFromCart = async (cartItemId) => {
//     try {
//       await axios.delete(
//         `http://localhost:8080/carts/remove/${cartItemId}`,
//         { headers: { Authorization: `Bearer ${token}` } }
//       );
//       fetchCartItems(orderId);
//       fetchOrderDetails(orderId);
//     } catch (err) {
//       console.error("Error removing from cart:", err);
//     }
//   };


//   // Calculate total on frontend (optional; backend total is main source)
//   const subtotal = cartItems.reduce(
//     (total, ci) => total + (ci.foodItem?.price || 0) * ci.quantity,
//     0
//   );

//   return (
//     <div className="cart-page">
//       <h2>My Cart</h2>

//       {cartItems.length === 0 ? (
//         <p>Your cart is empty.</p>
//       ) : (
//         <>
//           {cartItems.map((ci) => (
//             <div
//               key={ci.id}
//               style={{
//                 display: "flex",
//                 alignItems: "center",
//                 justifyContent: "space-between",
//                 borderBottom: "1px solid #ccc",
//                 padding: "10px 0",
//               }}
//             >
//               <span>{ci.foodItem?.itemName}</span>
//               <div style={{ display: "flex", alignItems: "center" }}>
//                 <button onClick={() => decreaseQty(ci.id)}>-</button>
//                 <span style={{ margin: "0 10px" }}>{ci.quantity}</span>
//                 <button onClick={() => increaseQty(ci.id)}>+</button>
//               </div>
//               <span>₹{ci.foodItem?.price}</span>
//               <button
//                 style={{
//                   color: "red",
//                   border: "none",
//                   background: "none",
//                 }}
//                 onClick={() => handleRemoveFromCart(ci.id)}
//               >
//                 ✖
//               </button>
//             </div>
//           ))}

//           <div
//             style={{
//               marginTop: "20px",
//               display: "flex",
//               justifyContent: "space-between",
//             }}
//           >
//             <strong>Total:</strong>
//             <strong>₹{subtotal}</strong>
//           </div>

//           <div style={{ marginTop: "20px", display: "flex", gap: "10px" }}>
//             <button onClick={() => navigate(`/restaurants/getrestaurant/${restaurantId}/food-items`)}>
//               Continue Shopping   
//             </button>
//             <button
//               style={{ backgroundColor: "green", color: "white" }}
//               onClick={() => navigate(`/checkout/${orderId}`)}
//             >
//               Continue to Payment
//             </button>
//           </div>
//         </>
//       )}
//     </div>
//   );
// };

// export default PlaceOrderPage;

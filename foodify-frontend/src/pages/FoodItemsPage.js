import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
import './FoodItemsPage.css';
import { addToCart } from '../services/cartService';
import { getRole } from '../services/authService';

const FoodItemsPage = () => {
  const { restaurantId } = useParams();
  const navigate = useNavigate();
 
  const [restaurantData, setRestaurantData] = useState(null);
  const [userRole, setUserRole] = useState(null);

  useEffect(() => {
    const role = getRole();
    setUserRole(role);

    const fetchRestaurantMenu = async () => {
      try {
        const token = localStorage.getItem("token");

        const response = await axios.get(
          `http://localhost:8080/restaurants/getrestaurant/${restaurantId}/food_items`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        setRestaurantData(response.data);
      } catch (error) {
        console.error("Error fetching restaurant menu:", error);
      }
    };

    fetchRestaurantMenu();
  }, [restaurantId]);

  const handleAddToCart = async (item) => {
    const token = localStorage.getItem("token");
    const userId = localStorage.getItem("userId");

    if (!token || !userId) {
      alert("Please log in to add items to the cart.");
      return;
    }

    const cartItem = {
      quantity: 1,
      userId: Number(userId),
    };

    try {
      await addToCart(item.id, cartItem, token);
      alert("Item added to cart successfully!");
    } catch (error) {
      console.error("Error adding to cart:", error);
      alert("Failed to add item to cart.");
    }
  };

  const handleOrderNow = (item) => {
    navigate(`/order/${restaurantId}`, { state: { item } });
  };

  const handleAddFood = () => {
    navigate(`/add-food-item/${restaurantId}`);
  };

  const handleUpdateFood = (itemId) => {
    navigate(`/restaurants/${restaurantId}/update-food/${itemId}`);
  };

  const handleDeleteFood = async (itemId) => {
    if (!window.confirm("Are you sure you want to delete this food item?")) return;

    try {
      const token = localStorage.getItem("token");

      await axios.delete(`http://localhost:8080/food_items/${itemId}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      alert("Item deleted successfully!");
      setRestaurantData(prev => ({
        ...prev,
        foodItems: prev.foodItems.filter(item => item.id !== itemId)
      }));
    } catch (error) {
      console.error("Error deleting food item:", error);
      alert("Failed to delete item.");
    }
  };

  if (!restaurantData || userRole === null) {
    return <div className="food-items-page">Loading...</div>;
  }

  const { name, description, address, foodItems: menuItems } = restaurantData;

  return (
    <div className="food-items-page">
      <h2>{name}</h2>
      <p><strong>Description:</strong> {description}</p>
      <p><strong>Address:</strong> {`${address.adrLine1}, ${address.city}, ${address.state}`}</p>
      <p><strong>Phone:</strong> {address.phone}</p>

      {userRole === 'ROLE_ADMIN' && (
        <div className="admin-actions">
          <button onClick={handleAddFood} className="btn btn-add">+ Add Food Item</button>
        </div>
      )}

      <h3>Menu</h3>
      {menuItems.length === 0 ? (
        <p>No food items available.</p>
      ) : (
        <ul className="food-list">
          {menuItems.map((item) => (
            <li key={item.id} className="food-item">
              <div className="food-info">
                <strong>{item.itemName}</strong> - ₹{item.price}<br />
                <em>{item.itemDescription}</em><br />
                <span>{item.veg ? '🌱 Veg' : '🍖 Non-Veg'} | {item.category}</span>

                <div className="button-group">
                  {(userRole === "ROLE_CUSTOMER" || userRole === "ROLE_ADMIN") && (
                    <>
                      <button onClick={() => handleAddToCart(item)} className="btn btn-cart">Add to Cart</button>
                      <button onClick={() => handleOrderNow(item)} className="btn btn-order">Order Now</button>
                    </>
                  )}

                  {userRole === "ROLE_ADMIN" && (
                    <>
                      <button onClick={() => handleUpdateFood(item.id)} className="btn btn-update">Update</button>
                      <button onClick={() => handleDeleteFood(item.id)} className="btn btn-delete">Delete</button>
                    </>
                  )}
                </div>
              </div>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default FoodItemsPage;

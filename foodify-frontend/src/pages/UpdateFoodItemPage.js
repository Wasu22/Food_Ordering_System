import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { getToken } from '../services/authService';

const UpdateFoodItemPage = () => {
  const { restaurantId, itemId } = useParams(); // ✅ FIXED: get itemId and restaurantId from URL
  const navigate = useNavigate();

  const [foodItem, setFoodItem] = useState({
    name: '',
    price: '',
    description: '',
    category: 'FASTFOOD', // Default
    isVeg: false,
  });

  // Fetch existing food item data
  useEffect(() => {
    axios
      .get(`http://localhost:8080/food_items/${itemId}`)
      .then((response) => {
        setFoodItem(response.data);
      })
      .catch((error) => {
        console.error('Error fetching food item:', error);
      });
  }, [itemId]);

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFoodItem((prev) => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.put(`http://localhost:8080/food_items/${itemId}`, foodItem,
  {
    headers: {
      Authorization: `Bearer ${getToken()}`
    }
  });
      alert('Food item updated successfully!');
      //navigate(`/restaurants/${restaurantId}`); // Redirect to restaurant's page
      navigate(`/restaurants/getrestaurant/${restaurantId}/food-items`);

    } catch (error) {
      console.error('Error updating food item:', error);
      alert('Failed to update food item.');
    }
  };

  return (
    <div className="form-container">
      <h2>Update Food Item</h2>
      <form onSubmit={handleSubmit}>
        <label>Name:</label>
        <input
          type="text"
          name="name"
          value={foodItem.name}
          onChange={handleChange}
          required
        /><br/>

        <label>Price:</label>
        <input
          type="number"
          name="price"
          value={foodItem.price}
          onChange={handleChange}
          required
        /><br/>

        <label>Description:</label>
        <textarea
          name="description"
          value={foodItem.description}
          onChange={handleChange}
          required
        /><br/>

        <label>Category:</label>
        <select
          name="category"
          value={foodItem.category}
          onChange={handleChange}
          required
        >
          <option value="FASTFOOD">FASTFOOD</option>
          <option value="CHINESES">CHINESES</option>
          <option value="SOUTHINDIAN">SOUTHINDIAN</option>
          <option value="DESSERT">DESSERT</option>
          <option value="BEVERAGES">BEVERAGES</option>
        </select><br/>

        <label>
          <input
            type="checkbox"
            name="isVeg"
            checked={foodItem.isVeg}
            onChange={handleChange}
          />
          veg
        </label><br/><br/>

        <button type="submit">Update</button>
      </form><br/>
    </div>
  );
};

 export default UpdateFoodItemPage;

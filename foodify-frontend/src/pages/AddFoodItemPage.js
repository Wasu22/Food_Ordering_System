import React, { useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';

const AddFoodItemPage = () => {
  const { restaurantId } = useParams();
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    itemName: '',
    itemDescription: '',
    category: '',
    price: '',
    veg: true
  });

  const categoryOptions = ["FASTFOOD", "CHINESES", "SOUTHINDIAN", "DESSERT", "BEVERAGES"];

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    const val = type === 'checkbox' ? checked : value;
    setFormData({ ...formData, [name]: val });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem('token');

    try {
      await axios.post(`http://localhost:8080/food_items`, {
        ...formData,
        restaurantId: restaurantId
      }, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      alert("Food item added successfully");
      navigate(`/restaurants/getrestaurant/${restaurantId}/food_items`);
    } catch (err) {
      console.error(err);
      alert("Error adding food item");
    }
  };

  return (
    <div className="form-container">
      <h2>Add New Food Item</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="itemName"
          placeholder="Item Name"
          value={formData.itemName}
          onChange={handleChange}
          required
        /><br/>

        <textarea
          name="itemDescription"
          placeholder="Description"
          value={formData.itemDescription}
          onChange={handleChange}
          required
        /><br/>

        <div className="form-group">
          <label>Category:</label>
          <select
            name="category"
            value={formData.category}
            onChange={handleChange}
            required
          >
            <option value="">-- Select Category --</option>
            {categoryOptions.map((cat, index) => (
              <option key={index} value={cat}>{cat}</option>
            ))}
          </select>
        </div>
        <br/>

        <input
          type="number"
          name="price"
          placeholder="Price"
          value={formData.price}
          onChange={handleChange}
          required
        /><br/>

        <label>
          <input
            type="checkbox"
            name="veg"
            checked={formData.veg}
            onChange={handleChange}
          />
          Veg
        </label><br/><br/>

        <button type="submit">Add Food</button>
      </form><br/>
    </div>
  );
};

export default AddFoodItemPage;

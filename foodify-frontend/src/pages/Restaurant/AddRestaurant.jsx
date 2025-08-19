import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom'; 

const AddRestaurantPage = () => {
  const navigate = useNavigate(); 

  const [restaurant, setRestaurant] = useState({
    name: '',
    description: '',
    status: true,
    address: {
      adrLine1: '',
      adrLine2: '',
      city: '',
      state: '',
      country: '',
      zipCode: '',
      phone: '',
    },
  });

  const handleChange = (e) => {
    const { name, value } = e.target;

    if (name in restaurant.address) {
      setRestaurant((prev) => ({
        ...prev,
        address: {
          ...prev.address,
          [name]: value,
        },
      }));
    } else {
      setRestaurant((prev) => ({
        ...prev,
        [name]: value,
      }));
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const token = localStorage.getItem("token");
    if (!token) {
      alert("You must be logged in to add a restaurant.");
      return;
    }

    try {
      const res = await axios.post(
        'http://localhost:8080/restaurants',
        restaurant,
        {
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        }
      );

      alert('Restaurant added successfully!');
      console.log(res.data);

      navigate('/restaurants');
    } catch (err) {
      console.error('Error adding restaurant:', err.response?.data || err.message);
      alert('Failed to add restaurant. Check console for details.');
    }
  };

  return (
    <div className="container">
      <h2>Add New Restaurant</h2>
      <form onSubmit={handleSubmit}>
        <input type="text" name="name" placeholder="Restaurant Name" value={restaurant.name} onChange={handleChange} required />
        <br />
        <textarea name="description" placeholder="Description" value={restaurant.description} onChange={handleChange} required />
        <br />
        <input type="text" name="adrLine1" placeholder="Address Line 1" value={restaurant.address.adrLine1} onChange={handleChange} required />
        <br />
        <input type="text" name="adrLine2" placeholder="Address Line 2" value={restaurant.address.adrLine2} onChange={handleChange} />
        <br />
        <input type="text" name="city" placeholder="City" value={restaurant.address.city} onChange={handleChange} required />
        <br />
        <input type="text" name="state" placeholder="State" value={restaurant.address.state} onChange={handleChange} required />
        <br />
        <input type="text" name="country" placeholder="Country" value={restaurant.address.country} onChange={handleChange} required />
        <br />
        <input type="text" name="zipCode" placeholder="Zip Code" value={restaurant.address.zipCode} onChange={handleChange} required />
        <br />
        <input type="text" name="phone" placeholder="Phone" value={restaurant.address.phone} onChange={handleChange} required />
        <br />
        <button type="submit">Add Restaurant</button>
      </form>
    </div>
  );
};

export default AddRestaurantPage;

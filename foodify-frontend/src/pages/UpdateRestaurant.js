import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate, useParams } from 'react-router-dom';

const UpdateRestaurant = () => {
  const { id } = useParams(); // restaurantId from route
  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  const [formData, setFormData] = useState({
    name: '',
    description: '',
    address: {
      city: '',
      state: '',
      street: '',
      zipcode: ''
    }
  });

  const [loading, setLoading] = useState(true);

  // Fetch existing restaurant details to pre-fill
  useEffect(() => {
    const fetchRestaurant = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/restaurants/getrestaurant/${id}`, {
          headers: { Authorization: `Bearer ${token}` }
        });
        setFormData(response.data);
      } catch (error) {
        console.error("Error fetching restaurant:", error);
        alert("Failed to fetch restaurant details");
        navigate('/restaurants');
      } finally {
        setLoading(false);
      }
    };
    fetchRestaurant();
  }, [id, token, navigate]);

  // Handle input changes
  const handleChange = (e) => {
    const { name, value } = e.target;

    if (["city", "state", "street", "zipcode"].includes(name)) {
      setFormData(prev => ({
        ...prev,
        address: {
          ...prev.address,
          [name]: value
        }
      }));
    } else {
      setFormData(prev => ({
        ...prev,
        [name]: value
      }));
    }
  };

  // Handle form submit
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.put(`http://localhost:8080/restaurants/${id}`, formData, {
        headers: { Authorization: `Bearer ${token}` }
      });
      alert("Restaurant updated successfully");
      navigate('/restaurants');
    } catch (error) {
      console.error("Error updating restaurant:", error);
      alert("Failed to update restaurant");
    }
  };

  if (loading) return <p>Loading restaurant details...</p>;

  return (
    <div className="container mt-4">
      <h2>Update Restaurant</h2>
      <form onSubmit={handleSubmit} className="card p-4">
        <div className="mb-3">
          <label className="form-label">Restaurant Name</label>
          <input
            type="text"
            name="name"
            className="form-control"
            value={formData.name}
            onChange={handleChange}
            required
          />
        </div>

        <div className="mb-3">
          <label className="form-label">Description</label>
          <textarea
            name="description"
            className="form-control"
            value={formData.description}
            onChange={handleChange}
            required
          />
        </div>

        {/* Address fields */}
        <div className="mb-3">
          <label className="form-label">Street</label>
          <input
            type="text"
            name="street"
            className="form-control"
            value={formData.address.street}
            onChange={handleChange}
          />
        </div>

        <div className="mb-3">
          <label className="form-label">City</label>
          <input
            type="text"
            name="city"
            className="form-control"
            value={formData.address.city}
            onChange={handleChange}
          />
        </div>

        <div className="mb-3">
          <label className="form-label">State</label>
          <input
            type="text"
            name="state"
            className="form-control"
            value={formData.address.state}
            onChange={handleChange}
          />
        </div>

        <div className="mb-3">
          <label className="form-label">Zip Code</label>
          <input
            type="text"
            name="zipcode"
            className="form-control"
            value={formData.address.zipcode}
            onChange={handleChange}
          />
        </div>

        <button type="submit" className="btn btn-primary">
          Update Restaurant
        </button>
      </form>
    </div>
  );
};

export default UpdateRestaurant;

import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './Restaurant.css';
import { useNavigate } from 'react-router-dom';

const Restaurant = () => {
  const [restaurants, setRestaurants] = useState([]);
  const [userRole, setUserRole] = useState(null);
  const navigate = useNavigate();
       const token = localStorage.getItem('token');

  useEffect(() => {

    if (token) {
      try {
        const decodedPayload = JSON.parse(atob(token.split('.')[1]));
        const role = decodedPayload.role;
        setUserRole(role);
      } catch (error) {
        console.error('Invalid token format', error);
        setUserRole(null);
      }
    }

    fetchRestaurants();
  }, []);

  const fetchRestaurants = async () => {
    try {
      const token = localStorage.getItem("token");

      const response = await axios.get("http://localhost:8080/restaurants/getrestaurant", {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      setRestaurants(response.data);
    } catch (error) {
      console.error("Error fetching restaurants:", error);
    }
  };

  const handleDelete = async (restaurantId) => {
    if (!window.confirm("Are you sure you want to delete this restaurant?")) return;
    try {
      await axios.delete(`http://localhost:8080/restaurants/${restaurantId}`,{
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      alert("Restaurant deleted");
      fetchRestaurants();
    } catch (err) {
      alert("Error deleting restaurant");
    }
  };

  return (
    <div className="restaurant-container">
      <h2>Restaurants</h2>

      {userRole === 'ROLE_ADMIN' && (
        <button
          className="btn btn-success mb-3"
          onClick={() => navigate('/restaurants/add')}
        >
          Add New Restaurant
        </button>
      )}

      {restaurants.length === 0 ? (
        <p>No restaurants available</p>
      ) : (
        restaurants.map((rest) => (
          <div className="restaurant-card" key={rest.id}>
            <div className="restaurant-info">
              <h4>{rest.name}</h4>
              <p>{rest.description}</p>
              <p><strong>Location:</strong> {rest.address?.city}, {rest.address?.state}</p>

              <button
                className="btn btn-info mt-2 me-2"
                onClick={() => navigate(`/restaurants/getrestaurant/${rest.id}/food-items`)}
              >
                View Food Items
              </button>

              <button
                className="btn btn-warning mt-2"
                onClick={() => navigate(`/reviews/${rest.id}`)}
              >
                View Reviews
              </button>

              {userRole === 'ROLE_ADMIN' && (
                <div className="admin-controls mt-2">
                  <button
                    className="btn btn-primary me-2"
                    onClick={() => navigate(`/restaurants/update/${rest.id}`)}
                  >
                    Edit
                  </button>
                  <button
                    className="btn btn-danger"
                    onClick={() => handleDelete(rest.id)}
                  >
                    Delete
                  </button>
                </div>
              )}
            </div>
          </div>
        ))
      )}
    </div>
  );
};

export default Restaurant;

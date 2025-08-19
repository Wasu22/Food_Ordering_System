import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import AddRestaurantPage from '../pages/Restaurant/AddRestaurantPage';
// import other pages as needed

const AppRoutes = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/restaurants/add" element={<AddRestaurantPage />} />
        {/* Add more routes here */}
      </Routes>
    </BrowserRouter>
  );
};

export default AppRoutes;
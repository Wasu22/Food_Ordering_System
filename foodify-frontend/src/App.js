// import React from 'react';
// import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

// import Header from './components/MyHeader';
// import Footer from './components/MyFooter';
// import HomePage from './pages/HomePage';
// import SignIn from './pages/Login';
// import SignUp from './pages/SignUp';
// import MainNavBar from './components/MainNavBar';
// import FoodItemsPage from './pages/FoodItemsPage'
// import Restaurant from './pages/Restaurant'
// import ContactUs from './pages/ContactUs';
// import About from './pages/About/About';
// import AddRestaurantPage from "./pages/Restaurant/AddRestaurant";
// import AddFoodItemPage from './pages/AddFoodItemPage';
// import UpdateFoodItemPage from './pages/UpdateFoodItemPage';


// const App = () => {
//   return (
//     <Router>
//       <MainNavBar />
//       {/* <Header /> */}

//       <Routes>
//         <Route path="/" element={<HomePage />} />
//         <Route path="/users/signin" element={<SignIn />} />
//         <Route path="/users/signup" element={<SignUp />} />
//         <Route path="/restaurants" element={<Restaurant />} />
//         <Route path="/restaurants/getrestaurant/:restaurantId/food-items" element={<FoodItemsPage />} />
//         <Route path="/restaurants/add" element={<AddRestaurantPage/>} />
//         <Route path="/add-food-item/:restaurantId" element={<AddFoodItemPage />} />
//         <Route path="/restaurants/:restaurantId/update-food/:itemId" element={<UpdateFoodItemPage />} />
// {/* <Route
//   path="/restaurants/:restaurantId/update-food/:itemId"
//   element={<UpdateFoodItemPage />}
// /> */}
//         <Route path="/about" element={<About/>} />
//         <Route path="/contact" element={<ContactUs />} />
//       </Routes>

//       <Footer />
//     </Router>
//   );
// };

// export default App;


import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import MainNavBar from './components/MainNavBar';
import Footer from './components/MyFooter';

// Pages
import HomePage from './pages/HomePage';
import SignIn from './pages/Login';
import SignUp from './pages/SignUp';
import Restaurant from './pages/Restaurant';
import FoodItemsPage from './pages/FoodItemsPage';
import AddRestaurantPage from './pages/Restaurant/AddRestaurant';
import AddFoodItemPage from './pages/AddFoodItemPage';
import UpdateFoodItemPage from './pages/UpdateFoodItemPage';
import ContactUs from './pages/ContactUs';
import About from './pages/About/About';
import RestaurantReviewPage from './pages/RestaurantReviewPage'
import PlaceOrderPage from './pages/PlaceOrderPage'
import UpdateRestaurant from './pages/UpdateRestaurant'
import CheckoutPage from "./pages/CheckoutPage";

const App = () => {
  return (
    <Router>
      <MainNavBar />

      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/users/signin" element={<SignIn />} />
        <Route path="/users/signup" element={<SignUp />} />

        <Route path="/restaurants" element={<Restaurant />} />
        <Route path="/restaurants/add" element={<AddRestaurantPage />} />
        <Route path="/restaurants/getrestaurant/:restaurantId/food-items" element={<FoodItemsPage />} />
        <Route path="/add-food-item/:restaurantId" element={<AddFoodItemPage />} />
        <Route path="/restaurants/:restaurantId/update-food/:itemId" element={<UpdateFoodItemPage />} />
        <Route path="/reviews/:restaurantId" element={<RestaurantReviewPage />} />
        <Route path="/order/:restaurantId" element={<PlaceOrderPage />} />
        <Route path="/restaurants/update/:id" element={<UpdateRestaurant />} />
        <Route path="/checkout/:orderId" element={<CheckoutPage />} />


        <Route path="/about" element={<About />} />
        <Route path="/contact" element={<ContactUs />} />
      </Routes>

      <Footer />
    </Router>
  );
};

export default App;

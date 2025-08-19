// // // // src/services/cartService.js
// // // import axios from 'axios';

// // // export const addToCart = async (cartItem) => {
// // //   const token = localStorage.getItem("token");

// // //   const response = await axios.post(
// // //     'http://localhost:8080/carts/add',
// // //     cartItem,
// // //     {
// // //       headers: {
// // //         Authorization: `Bearer ${token}`,
// // //          'Content-Type': 'application/json'
// // //       },
// // //     }
// // //   );

// // //   return response.data;
// // // };

// // import axios from 'axios';

// // const BASE_URL = 'http://localhost:8080/carts';

// // // export const addToCart = async (cartData) => {
// // //   return await axios.post(`${BASE_URL}/add`, cartData);
// // // };

// // export const addToCart = async (cartItem) => {
// //   const token = localStorage.getItem("token"); // If using JWT

// //   // return await axios.post(
// //   //   'http://localhost:8080/cart/add',
// //   //   cartItem,
// //   //   {
// //   //     headers: {
// //   //       Authorization: `Bearer ${token}`, // Optional if needed
// //   //     },
// //   //   }
// //   // );

// //    const response = await axios.post(
// //     `${BASE_URL}/add`,  // ✅ consistent usage
// //     cartItem,
// //     {
// //       headers: {
// //         Authorization: `Bearer ${token}`,
// //         'Content-Type': 'application/json', // ✅ explicitly set
// //       },
// //     }
// //   );

// //   return response.data;

// // };

// // export const removeFromCart = async (cartId) => {
// //   return await axios.delete(`${BASE_URL}/remove/${cartId}`);
// // };

// // export const getCartByOrderId = async (orderId) => {
// //   return await axios.get(`${BASE_URL}/order/${orderId}`);
// // };


// // src/services/cartService.js
// import axios from 'axios';

// const BASE_URL = 'http://localhost:8080/carts';

// // export const addToCart = async (cartItem) => {
// //   const token = localStorage.getItem("token");

// //   return await axios.post(
// //     `${BASE_URL}/add`,
// //     cartItem,
// //     {
// //       headers: {
// //         Authorization: `Bearer ${token}`,
// //         'Content-Type': 'application/json'
// //       },
// //     }
// //   );
// // };

// // export const addToCart = async (foodItemId, cartItem, token) => {
// //   return await axios.post(`${BASE_URL}/add/${foodItemId}`, cartItem, {
// //     headers: {
// //       Authorization: `Bearer ${token}`,
// //       "Content-Type": "application/json",
// //     },
// //   });
// // };


// export const addToCart = async (foodItemId, cartItem) => {
//   const token = localStorage.getItem("token");
//   return await axios.post(`${BASE_URL}/add/${foodItemId}`, cartItem, {
//     headers: {
//       Authorization: `Bearer ${token}`,
//       "Content-Type": "application/json",
//     },
//   });
// };



// export const removeFromCart = async (cartId) => {
//   const token = localStorage.getItem("token");

//   return await axios.delete(`${BASE_URL}/remove/${cartId}`, {
//     headers: {
//       Authorization: `Bearer ${token}`,
//     },
//   });
// };

// export const getCartByOrderId = async (orderId) => {
//   const token = localStorage.getItem("token");

//   return await axios.get(`${BASE_URL}/order/${orderId}`, {
//     headers: {
//       Authorization: `Bearer ${token}`,
//     },
//   });
// };


// src/services/cartService.js
import axios from 'axios';

const BASE_URL = 'http://localhost:8080';

export const addToCart = async (foodItemId, cartItem, token) => {
  try {
    const response = await axios.post(
      `${BASE_URL}/carts/add/${foodItemId}`, // ✅ Consistent endpoint
      cartItem,
      {
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      }
    );
    return response.data;
  } catch (error) {
    console.error('Cart service error:', error);
    throw error;
  }
};

export const getCartItems = async (userId, token) => {
  try {
    const response = await axios.get(`${BASE_URL}/cart/${userId}`, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    return response.data;
  } catch (error) {
    console.error('Get cart error:', error);
    throw error;
  }
};

export const removeFromCart = async (cartItemId, token) => {
  try {
    const response = await axios.delete(`${BASE_URL}/cart/${cartItemId}`, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    return response.data;
  } catch (error) {
    console.error('Remove from cart error:', error);
    throw error;
  }
};
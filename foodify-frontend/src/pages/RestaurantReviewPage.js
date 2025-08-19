// import React, { useEffect, useState } from "react";
// import axios from "axios";
// import { useParams } from "react-router-dom";

// const RestaurantReviewPage = () => {
//   const { restaurantId } = useParams();
//   const [reviews, setReviews] = useState([]);
//   const [comment, setComment] = useState("");
//   const [rating, setRating] = useState("GOOD"); // default enum value

//   const token = localStorage.getItem("token");

//   useEffect(() => {
//     const fetchReviews = async () => {
//       try {
//         const res = await axios.get(
//           `http://localhost:8080/restaurants/getrestaurant/reviews/${restaurantId}`,
//           {
//             headers: {
//               Authorization: `Bearer ${token}`,
//             },
//           }
//         );
//         setReviews(res.data);
//       } catch (error) {
//         console.error("Error fetching reviews:", error);
//       }
//     };

//     fetchReviews();
//   }, [restaurantId, token]);

//   const handleSubmit = async (e) => {
//     e.preventDefault();
//     try {
//       await axios.put(
//         `http://localhost:8080/restaurants/getrestaurant/reviews/${restaurantId}`,
//         {
//           comment,
//           rating, // sent as enum string: "GOOD", "EXCELLENT", etc.
//         },
//         {
//           headers: {
//             Authorization: `Bearer ${token}`,
//           },
//         }
//       );
//       alert("Review added successfully");
//       setComment("");
//       setRating("GOOD");
//       // Refresh reviews
//       const res = await axios.get(
//         `http://localhost:8080/restaurants/getrestaurant/reviews/${restaurantId}`,
//         {
//           headers: {
//             Authorization: `Bearer ${token}`,
//           },
//         }
//       );
//       setReviews(res.data);
//     } catch (error) {
//       console.error("Error submitting review:", error);
//       alert("Failed to submit review");
//     }
//   };

//   return (
//     <div style={{ padding: "30px" }}>
//       <h2>Restaurant Reviews</h2>

//       <form onSubmit={handleSubmit} style={{ marginBottom: "30px" }}>
//         <textarea
//           placeholder="Write your review..."
//           value={comment}
//           onChange={(e) => setComment(e.target.value)}
//           rows={4}
//           style={{ width: "100%", padding: "10px", marginBottom: "10px" }}
//           required
//         />

//         <select
//           value={rating}
//           onChange={(e) => setRating(e.target.value)}
//           required
//           style={{ padding: "8px", marginBottom: "10px" }}
//         >
//           <option value="POOR">Poor</option>
//           <option value="AVERAGE">Average</option>
//           <option value="GOOD">Good</option>
//           <option value="VERY_GOOD">Very Good</option>
//           <option value="EXCELLENT">Excellent</option>
//         </select>

//         <br />
//         <button type="submit" style={{ padding: "10px 20px" }}>
//           Submit Review
//         </button>
//       </form>

//       <h3>All Reviews:</h3>
//       {reviews.length === 0 ? (
//         <p>No reviews yet.</p>
//       ) : (
//         reviews.map((review, index) => (
//           <div
//             key={index}
//             style={{
//               border: "1px solid #ccc",
//               padding: "15px",
//               borderRadius: "8px",
//               marginBottom: "10px",
//               backgroundColor: "#f9f9f9",
//             }}
//           >
//             <p>
//               <strong>Comment:</strong> {review.comment}
//             </p>
//             {review.rating && (
//               <p>
//                 <strong>Rating:</strong> {review.rating}
//               </p>
//             )}
//           </div>
//         ))
//       )}
//     </div>
//   );
// };

// export default RestaurantReviewPage;


import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";

const RestaurantReviewPage = () => {
  const { restaurantId } = useParams();
  const [reviews, setReviews] = useState([]);
  const [comment, setComment] = useState("");
  const [ratingEnum, setRatingEnum] = useState("GOOD"); // enum name
  const [starsSelected, setStarsSelected] = useState(3); // numeric value for UI

  const token = localStorage.getItem("token");

  // Enum mapping: stars <-> enum name
  const ratingMap = {
    1: "POOR",
    2: "AVERAGE",
    3: "GOOD",
    4: "VERY_GOOD",
    5: "EXCELLENT",
  };

  const reverseRatingMap = Object.fromEntries(
    Object.entries(ratingMap).map(([num, name]) => [name, Number(num)])
  );

  useEffect(() => {
    const fetchReviews = async () => {
      try {
        const res = await axios.get(
          `http://localhost:8080/restaurants/getrestaurant/reviews/${restaurantId}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        setReviews(res.data);
      } catch (error) {
        console.error("Error fetching reviews:", error);
      }
    };

    fetchReviews();
  }, [restaurantId, token]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.put(
        `http://localhost:8080/restaurants/getrestaurant/reviews/${restaurantId}`,
        {
          comment,
          rating: ratingEnum, // send enum name
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      alert("Review added successfully");
      setComment("");
      setRatingEnum("GOOD");
      setStarsSelected(3);
      // Refresh reviews
      const res = await axios.get(
        `http://localhost:8080/restaurants/getrestaurant/reviews/${restaurantId}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      setReviews(res.data);
    } catch (error) {
      console.error("Error submitting review:", error);
      alert("Failed to submit review");
    }
  };

  const Star = ({ filled, onClick }) => (
    <span
      onClick={onClick}
      style={{
        cursor: "pointer",
        fontSize: "28px",
        color: filled ? "#FFD700" : "#ccc", // yellow or grey
        textShadow: filled ? "1px 1px 2px #b8860b" : "none", // 3D effect
      }}
    >
      ★
    </span>
  );

  return (
    <div style={{ padding: "30px" }}>
      <h2>Restaurant Reviews</h2>

      <form onSubmit={handleSubmit} style={{ marginBottom: "30px" }}>
        <textarea
          placeholder="Write your review..."
          value={comment}
          onChange={(e) => setComment(e.target.value)}
          rows={4}
          style={{ width: "100%", padding: "10px", marginBottom: "10px" }}
          required
        />

        {/* Star Rating Selector */}
        <div style={{ marginBottom: "10px" }}>
          {[1, 2, 3, 4, 5].map((num) => (
            <Star
              key={num}
              filled={num <= starsSelected}
              onClick={() => {
                setStarsSelected(num);
                setRatingEnum(ratingMap[num]);
              }}
            />
          ))}
        </div>

        <button type="submit" style={{ padding: "10px 20px" }}>
          Submit Review
        </button>
      </form>

      <h3>All Reviews:</h3>
      {reviews.length === 0 ? (
        <p>No reviews yet.</p>
      ) : (
        reviews.map((review, index) => (
          <div
            key={index}
            style={{
              border: "1px solid #ccc",
              padding: "15px",
              borderRadius: "8px",
              marginBottom: "10px",
              backgroundColor: "#f9f9f9",
            }}
          >
            <p>
              <strong>Comment:</strong> {review.comment}
            </p>
            {review.rating && (
              <p>
                <strong>Rating: </strong>
                {[...Array(reverseRatingMap[review.rating])].map((_, i) => (
                  <span
                    key={i}
                    style={{
                      color: "#FFD700",
                      fontSize: "20px",
                      textShadow: "1px 1px 2px #b8860b",
                    }}
                  >
                    ★
                  </span>
                ))}
              </p>
            )}
          </div>
        ))
      )}
    </div>
  );
};

export default RestaurantReviewPage;

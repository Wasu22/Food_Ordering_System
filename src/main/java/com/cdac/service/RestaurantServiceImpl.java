package com.cdac.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdac.custom_exception.ApiException;
import com.cdac.custom_exception.ResourceNotFoundException;
import com.cdac.dao.RestaurantDao;
import com.cdac.dao.ReviewDao;
import com.cdac.dto.AddRestaurantDTO;
import com.cdac.dto.ApiResponse;
import com.cdac.dto.RestaurantMenuDTO;
import com.cdac.dto.RestaurantRespDTO;
import com.cdac.dto.ReviewDto;
import com.cdac.entities.Restaurant;
import com.cdac.entities.Review;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
	
	private final RestaurantDao restaurantDao;
	private final ModelMapper modelMapper;
	@Autowired
	private ReviewDao reviewDao;


	@Override
	public List<RestaurantRespDTO> getAllRestaurants() {
		return restaurantDao.findByStatusTrueWithAddress()
		        .stream()
		        .map(restaurant -> modelMapper.map(restaurant, RestaurantRespDTO.class))
		        .toList();
		
	}

	@Override
	public ApiResponse deleteRestaurantDetail(Long restaurantId) {
		
		Restaurant restaurant = restaurantDao.findById(restaurantId)
				.orElseThrow(() -> new ResourceNotFoundException("invalid restaurant id !!!!!"));
		restaurant.setStatus(false);
		return new ApiResponse("soft deleted restaurant details ");
	}

	@Override
	public RestaurantRespDTO getRestaurantDetails(Long id) {
		Restaurant entity = restaurantDao.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Restaurant ID !!!!!"));
		return modelMapper.map(entity, RestaurantRespDTO.class);
	}

	@Override
	public ApiResponse updateDetails(Long id, AddRestaurantDTO dto) {
		Restaurant entity = restaurantDao.findById(id)
				.orElseThrow(() ->
				new ResourceNotFoundException("Invalid Restaurant ID : Update failed"));
	    if (!entity.getName().equals(dto.getName()) && restaurantDao.existsByName(dto.getName())) {
	        throw new ApiException("Dup Restaurant Name - update restaurant failed ");
	    }
		
		modelMapper.map(dto, entity);
		return new ApiResponse("restaurant details updated !");
	}

	@Override
	public ApiResponse addNewRestaurant(AddRestaurantDTO dto) {
		if (restaurantDao.existsByName(dto.getName()))
			throw new ApiException
			("Dup Restaurant Name - add restaurant failed");
		Restaurant entity = modelMapper.map(dto, Restaurant.class);
		entity.setStatus(true);
		Restaurant persistenRestaurant = restaurantDao.save(entity);
		return new ApiResponse
				("Added new restaurant with ID=" + persistenRestaurant.getId());
	}

	@Override
	public RestaurantMenuDTO getCompleteDetails(Long restaurantId) {
		Restaurant entity =
				restaurantDao.fetchCompleteDetails(restaurantId);
		entity.getFoodItems().size(); 
		return modelMapper.map(entity, RestaurantMenuDTO.class);
	}
	
//	@Override
//	public String addRestaurantReview(Long restaurantId, String comment) {
//	    Restaurant restaurant = restaurantDao.findById(restaurantId)
//	            .orElseThrow(() -> new ApiException("Restaurant not found"));
// 
//	    Review review = Review.builder()
//	            .comment(comment)
//	            .createdAt(LocalDateTime.now())
//	            .restaurant(restaurant)
//	            .build();
//
//	    reviewDao.save(review);
//	    return "Review added successfully.";
//	}
//	
	
	@Override
	public String addRestaurantReview(Long restaurantId, ReviewDto reviewDto) {
	    Restaurant restaurant = restaurantDao.findById(restaurantId)
	            .orElseThrow(() -> new ApiException("Restaurant not found"));

	    if (reviewDto == null || reviewDto.getComment() == null || reviewDto.getComment().trim().isEmpty()) {
	        throw new ApiException("Review comment cannot be empty");
	    }

	    Review review = Review.builder()
	            .comment(reviewDto.getComment())
	            .createdAt(LocalDateTime.now())
	            .restaurant(restaurant)
	            .build();

	    reviewDao.save(review);
	    return "Review added successfully.";
	}

//	public List<ReviewDto> getReviewsForRestaurant(Long restaurantId) {
//	    Restaurant restaurant = restaurantDao.findById(restaurantId)
//	            .orElseThrow(() -> new ApiException("Restaurant not found"));
//
//	    return restaurant.getReviews().stream()
//	            .map(review -> {
//	                ReviewDto dto = new ReviewDto();
//	                dto.setComment(review.getComment());
//	                return dto;
//	            }).toList();
//	}
	
	@Override
	public List<ReviewDto> getReviewsForRestaurant(Long restaurantId) {
	    Restaurant restaurant = restaurantDao.findById(restaurantId)
	            .orElseThrow(() -> new ApiException("Restaurant not found"));

	    List<Review> reviews = restaurant.getReviews();
	    
	    if (reviews == null || reviews.isEmpty()) {
	        return new ArrayList<>();
	    }

	    return reviews.stream()
	            .map(review -> {
	                ReviewDto dto = new ReviewDto();
	                dto.setComment(review.getComment());
	                dto.setRating(review.getRating());
	                return dto;
	            })
	            .toList();
	}



	  
}

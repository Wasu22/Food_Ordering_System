package com.cdac.service;

import java.util.List;

import com.cdac.dto.AddRestaurantDTO;
import com.cdac.dto.ApiResponse;
import com.cdac.dto.RestaurantMenuDTO;
import com.cdac.dto.RestaurantRespDTO;
import com.cdac.dto.ReviewDto;

public interface RestaurantService {
	List<RestaurantRespDTO> getAllRestaurants();
	ApiResponse deleteRestaurantDetail(Long restaurantId);
	RestaurantRespDTO getRestaurantDetails(Long id);
	ApiResponse updateDetails(Long id, AddRestaurantDTO restaurant);
	ApiResponse addNewRestaurant(AddRestaurantDTO transientRestaurant);
	RestaurantMenuDTO getCompleteDetails(Long restaurantId);
	String addRestaurantReview(Long restaurantId, ReviewDto reviewDto);
     List<ReviewDto> getReviewsForRestaurant(Long restaurantId);	 
}

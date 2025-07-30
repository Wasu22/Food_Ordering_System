package com.cdac.service;

import java.util.List;

import com.cdac.dto.AddRestaurantDTO;
import com.cdac.dto.ApiResponse;
import com.cdac.dto.RestaurantMenuDTO;
import com.cdac.dto.RestaurantRespDTO;

public interface RestaurantService {
	List<RestaurantRespDTO> getAllRestaurants();
	ApiResponse deleteRestaurantDetail(Long restaurantId);
	RestaurantRespDTO getRestaurantDetails(Long id);
	ApiResponse updateDetails(Long id, AddRestaurantDTO restaurant);
	ApiResponse addNewRestaurant(AddRestaurantDTO transientRestaurant);
	RestaurantMenuDTO getCompleteDetails(Long restaurantId);
}

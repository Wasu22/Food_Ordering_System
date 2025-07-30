package com.cdac.service;

import java.util.List;

import com.cdac.dto.ApiResponse;
import com.cdac.dto.FoodItemDTO;

public interface FoodItemService {
	ApiResponse addFoodItemToRestaurant(Long restaurantId, FoodItemDTO dto);
	ApiResponse updateFoodItem(Long foodItemId, FoodItemDTO dto);
	ApiResponse deleteFoodItem(Long foodItemId);
	FoodItemDTO getFoodItemById(Long foodItemId);
	List<FoodItemDTO> getAllFoodItems();

}

package com.cdac.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.cdac.dto.ApiResponse;
import com.cdac.dto.FoodItemRequestDTO;
import com.cdac.dto.FoodItemResponseDTO;

public interface FoodItemService {
	ApiResponse addFoodItemToRestaurant(FoodItemRequestDTO dto);
	ResponseEntity<FoodItemResponseDTO> updateFoodItem(Long foodItemId, FoodItemRequestDTO dto);
	ApiResponse deleteFoodItem(Long foodItemId);
	FoodItemRequestDTO getFoodItemById(Long foodItemId);
	List<FoodItemRequestDTO> getAllFoodItems();

}

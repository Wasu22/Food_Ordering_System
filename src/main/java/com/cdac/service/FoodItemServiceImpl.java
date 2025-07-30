package com.cdac.service;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdac.custom_exception.ResourceNotFoundException;
import com.cdac.dao.FoodItemDao;
import com.cdac.dao.RestaurantDao;
import com.cdac.dto.ApiResponse;
import com.cdac.dto.FoodItemDTO;
import com.cdac.entities.Category;
import com.cdac.entities.FoodItem;
import com.cdac.entities.Restaurant;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class FoodItemServiceImpl implements FoodItemService {
	
	private final FoodItemDao foodItemDao;
	private final ModelMapper modelMapper;
	private final RestaurantDao restaurantDao;

	@Override
	public ApiResponse addFoodItemToRestaurant(Long restaurantId,
			FoodItemDTO dto) {
		Restaurant restaurantEntity=
				restaurantDao.findById(restaurantId).orElseThrow(() -> 
				new ResourceNotFoundException("Invalid restaurant id - Food Item can't be added!!!!"));
		FoodItem foodItemEntity = modelMapper.map(dto, FoodItem.class);
		System.out.println("Mapped entity: " + foodItemEntity);
		restaurantEntity.addFoodItem(foodItemEntity);
		return new ApiResponse("New food item added to the restaurant");
	}

	@Override
	public ApiResponse updateFoodItem(Long foodItemId, FoodItemDTO dto) {
	    FoodItem foodItem = foodItemDao.findById(foodItemId)
	        .orElseThrow(() -> new ResourceNotFoundException("Invalid food item ID"));

	    foodItem.setItemName(dto.getItemName());
	    foodItem.setItemDescription(dto.getItemDescription());
	    foodItem.setVeg(dto.isVeg());
	    foodItem.setPrice(dto.getPrice());

	    // Optional: Handle category if it's in the DTO
	    try {
	        foodItem.setCategory(Category.valueOf(dto.getCategory().toUpperCase()));
	    } catch (IllegalArgumentException e) {
	        throw new IllegalArgumentException("Invalid category: " + dto.getCategory()
	            + ". Allowed values: " + Arrays.toString(Category.values()));
	    }
	    return new ApiResponse("Food item updated successfully");
	}

	@Override
	public ApiResponse deleteFoodItem(Long foodItemId) {
	    FoodItem foodItem = foodItemDao.findById(foodItemId)
	        .orElseThrow(() -> new ResourceNotFoundException("Invalid food item ID"));

	    foodItemDao.delete(foodItem);
	    return new ApiResponse("Food item deleted successfully");
	}

	@Override
	public FoodItemDTO getFoodItemById(Long foodItemId) {
	    FoodItem foodItem = foodItemDao.findById(foodItemId)
	        .orElseThrow(() -> new ResourceNotFoundException("Invalid food item ID"));

	    return modelMapper.map(foodItem, FoodItemDTO.class);
	}

	@Override
	public List<FoodItemDTO> getAllFoodItems() {
	    return foodItemDao.findAll()
	        .stream()
	        .map(foodItem -> modelMapper.map(foodItem, FoodItemDTO.class))
	        .toList();
	}

}

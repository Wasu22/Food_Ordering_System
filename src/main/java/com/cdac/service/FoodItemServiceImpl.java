package com.cdac.service;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdac.custom_exception.ResourceNotFoundException;
import com.cdac.dao.FoodItemDao;
import com.cdac.dao.RestaurantDao;
import com.cdac.dto.ApiResponse;
import com.cdac.dto.FoodItemRequestDTO;
import com.cdac.dto.FoodItemResponseDTO;
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
	public ApiResponse addFoodItemToRestaurant(FoodItemRequestDTO dto) {
		Restaurant restaurantEntity=
				restaurantDao.findById(dto.getId()).orElseThrow(() -> 
				new ResourceNotFoundException("Invalid restaurant id - Food Item can't be added!!!!"));
		FoodItem foodItemEntity = modelMapper.map(dto, FoodItem.class);
		foodItemEntity.setVeg(dto.isVeg());
		System.out.println("Mapped entity: " + foodItemEntity);
		restaurantEntity.addFoodItem(foodItemEntity);
		return new ApiResponse("New food item added to the restaurant");
	}

	@Override
	public ResponseEntity<FoodItemResponseDTO> updateFoodItem(Long foodItemId, FoodItemRequestDTO dto) {
	    FoodItem foodItem = foodItemDao.findById(foodItemId)
	        .orElseThrow(() -> new ResourceNotFoundException("Invalid food item ID"));

	   
	    modelMapper.map(dto, foodItem);
	    
	    
	    foodItem.setVeg(dto.isVeg());

	    if (dto.getCategory() != null) {
	        try {
	            foodItem.setCategory(Category.valueOf(dto.getCategory().toUpperCase()));
	        } catch (IllegalArgumentException e) {
	            throw new IllegalArgumentException("Invalid category: " + dto.getCategory() +
	                " Allowed values: " + Arrays.toString(Category.values()));
	        }
	    }

	    FoodItem updatedFoodItem = foodItemDao.save(foodItem);

	    FoodItemResponseDTO responseDTO = modelMapper.map(updatedFoodItem, FoodItemResponseDTO.class);
	    responseDTO.setFoodItemId(updatedFoodItem.getId());
	    responseDTO.setRestaurantId(updatedFoodItem.getMyRestaurant().getId());

	    return ResponseEntity.ok(responseDTO);
	}


	@Override
	public ApiResponse deleteFoodItem(Long foodItemId) {
	    FoodItem foodItem = foodItemDao.findById(foodItemId)
	        .orElseThrow(() -> new ResourceNotFoundException("Invalid food item ID"));

	    foodItemDao.delete(foodItem);
	    return new ApiResponse("Food item deleted successfully");
	}
	
	@Override
	public FoodItemRequestDTO getFoodItemById(Long foodItemId) {
	    FoodItem foodItem = foodItemDao.findById(foodItemId)
	        .orElseThrow(() -> new ResourceNotFoundException("Invalid food item ID"));

	    FoodItemRequestDTO dto = modelMapper.map(foodItem, FoodItemRequestDTO.class);
	    //dto.setRestaurantId(foodItem.getMyRestaurant().getId()); 

	    return dto;
	}


	@Override
	public List<FoodItemRequestDTO> getAllFoodItems() {
	    return foodItemDao.findAll()
	        .stream()
	        .map(foodItem -> modelMapper.map(foodItem, FoodItemRequestDTO.class))
	        .toList();
	}

}

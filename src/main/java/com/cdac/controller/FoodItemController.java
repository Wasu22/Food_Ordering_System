package com.cdac.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.dto.FoodItemRequestDTO;
import com.cdac.service.FoodItemService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/food_items")
@AllArgsConstructor
public class FoodItemController {
	
	private final FoodItemService foodItemService;
	
	@PostMapping
	@Operation(description = "Add new food item")
	public ResponseEntity<?> addFoodItem(@RequestBody FoodItemRequestDTO dto)
	{
		System.out.println(" in add food "+dto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body
				(foodItemService.addFoodItemToRestaurant
						(dto));
	}

	@PutMapping("/{foodItemId}")
	@Operation(description = "Update an existing food item")
	public ResponseEntity<?> updateFoodItem(@PathVariable Long foodItemId, @RequestBody FoodItemRequestDTO dto) {
	    return ResponseEntity.ok(foodItemService.updateFoodItem(foodItemId, dto));
	}

	@DeleteMapping("/{foodItemId}")
	@Operation(description = "Delete a food item")
	public ResponseEntity<?> deleteFoodItem(@PathVariable Long foodItemId) {
	    return ResponseEntity.ok(foodItemService.deleteFoodItem(foodItemId));
	}

	@GetMapping("/{foodItemId}")
	@Operation(description = "Get food item details by ID")
	public ResponseEntity<?> getFoodItem(@PathVariable Long foodItemId) {
	    return ResponseEntity.ok(foodItemService.getFoodItemById(foodItemId));
	}
	
	@GetMapping("/foodItems")
	@Operation(description = "Get all food items")
	public ResponseEntity<?> getAllFoodItems() {
	    return ResponseEntity.ok(foodItemService.getAllFoodItems());
	}


}

package com.cdac.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.dto.AddRestaurantDTO;
import com.cdac.dto.RestaurantRespDTO;
import com.cdac.service.RestaurantService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;

@RestController 
@RequestMapping("/restaurants")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
@Validated

public class RestaurantController {
	private final RestaurantService restaurantService;

	@GetMapping
	public  ResponseEntity<?> listAvailableRestaurants() {
		System.out.println("in list");
		List<RestaurantRespDTO> restaurants 
		= restaurantService.getAllRestaurants();
		if(restaurants.isEmpty())
			 return ResponseEntity
					 .status(HttpStatus.NO_CONTENT).build();
			
		return ResponseEntity.ok(restaurants);
	}


	@PostMapping
	@Operation(description = "Add New Resaturant")
	public ResponseEntity<?> addNewRestaurant(@RequestBody 
			AddRestaurantDTO dto) {
		System.out.println("in add " + dto);
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(restaurantService
						.addNewRestaurant(dto));

	}

	@DeleteMapping("/{restaurantId}") 
	public ResponseEntity<?> deleteDetails
	(@PathVariable  Long restaurantId) {
		System.out.println("in delete " + restaurantId);
		return ResponseEntity
				.ok(restaurantService.deleteRestaurantDetail(restaurantId));
	}

	@GetMapping("/{restaurantId}")
	@Operation(description = "Get restaurant details by ID")
	public ResponseEntity<?> getRestaurantDetails(
			@PathVariable @Min(1) @Max(100) Long restaurantId) {
		System.out.println("in get details " + restaurantId);
	
		return ResponseEntity.ok(
				restaurantService.getRestaurantDetails(restaurantId));

	}
	
	@PutMapping("/{restaurantId}")
	@Operation(description = "Update restaurant details(Partial or Complete)")
	public ResponseEntity<?> updateDetails
	(@PathVariable Long restaurantId,
			@RequestBody AddRestaurantDTO dto ) {
		System.out.println("in update "+restaurantId+" "+dto);
		return ResponseEntity.ok(
				restaurantService.updateDetails(restaurantId, dto));
	}	
	
	@GetMapping("/{restaurantId}/food_items")
	public ResponseEntity<?> fetchCompleteDetails(@PathVariable Long restaurantId) {
		System.out.println("in get completer details "+restaurantId);
		return ResponseEntity.ok(
				restaurantService.getCompleteDetails(restaurantId)
				);
	}

}

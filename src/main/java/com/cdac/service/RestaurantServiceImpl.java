package com.cdac.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdac.custom_exception.ApiException;
import com.cdac.custom_exception.ResourceNotFoundException;
import com.cdac.dao.RestaurantDao;
import com.cdac.dto.AddRestaurantDTO;
import com.cdac.dto.ApiResponse;
import com.cdac.dto.RestaurantMenuDTO;
import com.cdac.dto.RestaurantRespDTO;
import com.cdac.entities.Restaurant;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
	
	private final RestaurantDao restaurantDao;
	private final ModelMapper modelMapper;

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
	
	

}

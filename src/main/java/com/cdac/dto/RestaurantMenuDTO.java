package com.cdac.dto;

import java.util.ArrayList;
import java.util.List;

import com.cdac.entities.Address;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantMenuDTO extends BaseDTO{
	private String name;
	private AddressDTO address;	
	private String description;
	
	private List<FoodItemDTO> foodItems=new ArrayList<>();
}

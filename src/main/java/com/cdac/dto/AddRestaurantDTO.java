package com.cdac.dto;

import com.cdac.entities.Address;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddRestaurantDTO{
	private String name;
	private Address address;	
	private String description;	
}

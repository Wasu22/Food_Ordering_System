package com.cdac.dto;

import com.cdac.entities.Address;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class RestaurantRespDTO extends BaseDTO {
	
	private String name;
	private AddressDTO address;
	private String description;

	// for soft delete
	private boolean status;

}

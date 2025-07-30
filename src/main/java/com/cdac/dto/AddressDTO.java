package com.cdac.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {
	private String adrLine1;
	
	private String adrLine2;

	private String city;
	
	private String state;
	
	private String country;
	
	private String zipCode;
	
	private String phone;	
}

package com.cdac.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class FoodItemDTO extends BaseDTO{	
	private String itemName;	
	private String itemDescription;	
	private boolean isVeg;
	private int price;
	@Pattern(regexp = "BEVERAGES|CHINESES|DESSERT|FASTFOOD|SOUTHINDIAN", message = "Invalid category")
	private String category;

	}

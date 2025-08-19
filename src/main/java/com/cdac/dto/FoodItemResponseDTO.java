package com.cdac.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class FoodItemResponseDTO {
	
	private Long foodItemId;
	
    private Long restaurantId;

    private String itemName;

    private String itemDescription;

    private boolean isVeg;

    private int price;

    private String category;

    //private String imageBase64;

}

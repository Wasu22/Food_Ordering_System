package com.cdac.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FoodItemRequestDTO {

    @NotNull(message = "Restaurant ID is required")
    private Long id;

    @NotBlank(message = "Item name is required")
    private String itemName;

    private String itemDescription;

    private boolean isVeg;

    @Positive(message = "Price must be a positive value")
    private int price;

    @NotBlank(message = "Category is required")
    @Pattern(
        regexp = "BEVERAGES|CHINESE|DESSERT|FASTFOOD|SOUTHINDIAN",
        message = "Category must be one of: BEVERAGES, CHINESE, DESSERT, FASTFOOD, SOUTHINDIAN"
    )
    private String category;

    // Optional: Add base64-encoded image string or multipart file for item image
    //private String imageBase64;
}

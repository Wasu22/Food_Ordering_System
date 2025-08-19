package com.cdac.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDTO {
    private Long id;
    private int quantity;
    private double price;
    private double subTotal;
    private String foodItemName;
}

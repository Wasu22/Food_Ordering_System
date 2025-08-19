package com.cdac.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartResponseDTO {
    private Long cartId;
    private String foodName;
    private int quantity;
    private double price;
    private double subTotal;
}
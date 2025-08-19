package com.cdac.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequestDTO {
    private Long foodItemId;
    private int quantity;
    private Long userId; // used to fetch user's cart
}

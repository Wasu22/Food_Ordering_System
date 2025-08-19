package com.cdac.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.cdac.dto.ApiResponse;
import com.cdac.dto.CartDTO;
import com.cdac.dto.CartRequestDTO;
import com.cdac.dto.CartResponseDTO;
import com.cdac.entities.Cart;

public interface CartService {

	ResponseEntity<ApiResponse> addToCart(Long foodItemId,CartRequestDTO request);

	ResponseEntity<ApiResponse> removeFromCart(Long cartId);
	
	ResponseEntity<List<CartResponseDTO>> getCartByOrder(Long orderId);
	
	//void updateCartItemQuantity(Long cartItemId, int quantity);

	Cart updateQuantity(Long cartItemId, int quantity);
	 List<CartDTO> getCartItemsByUserId(Long userId);

}
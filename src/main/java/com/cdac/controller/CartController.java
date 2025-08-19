package com.cdac.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.dto.ApiResponse;
import com.cdac.dto.CartDTO;
import com.cdac.dto.CartRequestDTO;
import com.cdac.dto.UpdateCartQuantityDTO;
import com.cdac.service.CartService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/carts")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;
    
    @PostMapping("/add/{foodItemId}")
    @Operation(summary = "API to add item to cart using orderId")
    public ResponseEntity<ApiResponse> addToCart(@PathVariable Long foodItemId, @RequestBody CartRequestDTO request) {
        return cartService.addToCart(foodItemId,request);
    }
    
    @DeleteMapping("/remove/{cartId}")
    @Operation(summary = "API to remove item from cart by cartId")
    public ResponseEntity<ApiResponse> removeFromCart(@PathVariable Long cartId) {
        return cartService.removeFromCart(cartId);
    }
    
    @GetMapping("/order/{orderId}")
    @Operation(summary = "API to view cart items by orderId")
    public ResponseEntity<?> viewCartByOrder(@PathVariable Long orderId) {
        return cartService.getCartByOrder(orderId);
    }
    
 
    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<String> updateCartItemQuantity(
            @PathVariable Long cartItemId,
            @RequestBody UpdateCartQuantityDTO dto) {
        cartService.updateQuantity(cartItemId, dto.getQuantity());
        return ResponseEntity.ok("Cart item quantity updated successfully");
    }
    
    @GetMapping("/user/{userId}")
    public List<CartDTO> getCartItemsByUser(@PathVariable Long userId) {
        return cartService.getCartItemsByUserId(userId);
    }


}
package com.cdac.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdac.custom_exception.ResourceNotFoundException;
import com.cdac.dao.CartDao;
import com.cdac.dao.FoodItemDao;
import com.cdac.dao.OrderDao;
import com.cdac.dto.ApiResponse;
import com.cdac.dto.CartDTO;
import com.cdac.dto.CartRequestDTO;
import com.cdac.dto.CartResponseDTO;
import com.cdac.entities.Cart;
import com.cdac.entities.FoodItem;
import com.cdac.entities.Order;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartDao cartDao;
    private final FoodItemDao foodRepo;
    private final OrderDao orderDao;

    @Override
    public ResponseEntity<ApiResponse> addToCart(Long foodItemId,CartRequestDTO request) {
        FoodItem food = foodRepo.findById(foodItemId)
            .orElseThrow(() -> new RuntimeException("Food item not found"));

        // Create a cart item without linking it to an Order yet
        Cart cart = new Cart();
        cart.setFoodItem(food);
        cart.setQuantity(request.getQuantity());
        cart.setPrice(food.getPrice());
        cart.setSubTotal(food.getPrice() * request.getQuantity());
        
        // TODO: Associate cart with user - implement setUser() if needed
        // cart.setUser(userRepo.findById(request.getUserId()).orElseThrow(...));

        cartDao.save(cart);
        return ResponseEntity.ok(new ApiResponse("Item added to cart"));
    }

@Override
public ResponseEntity<ApiResponse> removeFromCart(Long cartId) {
        cartDao.deleteById(cartId);
        return ResponseEntity.ok(new ApiResponse("Item removed from cart"));
}

@Override
public ResponseEntity<List<CartResponseDTO>> getCartByOrder(Long orderId) {
    List<Cart> carts = cartDao.findByOrderId(orderId);
    System.out.println(carts);
    List<CartResponseDTO> response = carts.stream().map(cart -> {
        CartResponseDTO dto = new CartResponseDTO();
        dto.setCartId(cart.getId());
        dto.setFoodName(cart.getFoodItem().getItemName());
        dto.setQuantity(cart.getQuantity());
        dto.setPrice(cart.getPrice());
        dto.setSubTotal(cart.getSubTotal());
        return dto;
    }).collect(Collectors.toList());

    return ResponseEntity.ok(response);
}

//@Override
//public void updateCartItemQuantity(Long cartItemId, int quantity) {
//    Cart cartItem = cartDao.findById(cartItemId)
//            .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id: " + cartItemId));
//
//    cartItem.setQuantity(quantity);
//
//    // Update subtotal and price
//    double itemPrice = cartItem.getFoodItem().getPrice();
//    cartItem.setPrice(itemPrice); // price per item
//    cartItem.setSubTotal(itemPrice * quantity);
//
//    cartDao.save(cartItem);
//}

@Override
public Cart updateQuantity(Long cartItemId, int quantity) {
    Cart cart = cartDao.findById(cartItemId)
            .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

    cart.setQuantity(quantity);

    // Update the cart subtotal
    double newSubTotal = cart.getFoodItem().getPrice() * quantity;
    cart.setSubTotal(newSubTotal);

    // Recalculate the order total
    Order order = cart.getOrder();
    double newTotal = order.getCart().stream()
            .mapToDouble(Cart::getSubTotal)
            .sum();
    order.setOrderAmount(newTotal); // ✅ use orderAmount

    return cartDao.save(cart);
}

@Override
public List<CartDTO> getCartItemsByUserId(Long userId) {
    return cartDao.findByUserId(userId).stream().map(cart -> {
        CartDTO dto = new CartDTO();
        dto.setId(cart.getId());
        dto.setQuantity(cart.getQuantity());
        dto.setPrice(cart.getPrice());
        dto.setSubTotal(cart.getSubTotal());
        dto.setFoodItemName(cart.getFoodItem().getItemName()); // triggers lazy load here, while session is still open
        return dto;
    }).toList();
}



}
package com.cdac.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdac.custom_exception.ApiException;
import com.cdac.custom_exception.ResourceNotFoundException;
import com.cdac.dao.CartDao;
import com.cdac.dao.OrderDao;
import com.cdac.dao.PaymentDao;
import com.cdac.dao.RestaurantDao;
import com.cdac.dao.UserDao;
import com.cdac.dto.ApiResponse;
import com.cdac.dto.OrderDTO;
import com.cdac.entities.Cart;
import com.cdac.entities.Order;
import com.cdac.entities.OrderStatus;
import com.cdac.entities.Payment;
import com.cdac.entities.Restaurant;
import com.cdac.entities.User;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final UserDao userDao;
    private final RestaurantDao restaurantDao;
    private final PaymentDao paymentDao;
    private final ModelMapper modelMapper;
    private final CartDao cartDao;
    
    @Override
    public OrderDTO getOrderById(Long id) {
        Order order = orderDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        OrderDTO dto = modelMapper.map(order, OrderDTO.class);

        if (order.getCustomer() != null)
            dto.setCustomerId(order.getCustomer().getId());

        if (order.getRestaurant() != null)
            dto.setRestaurantId(order.getRestaurant().getId());

        if (order.getMyPayment() != null)
            dto.setPayment(order.getMyPayment());
        return dto;
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderDao.findAll().stream()
            .map(order ->{
                OrderDTO dto = modelMapper.map(order, OrderDTO.class);

                if (order.getCustomer() != null)
                    dto.setCustomerId(order.getCustomer().getId());

                if (order.getRestaurant() != null)
                    dto.setRestaurantId(order.getRestaurant().getId());

                if (order.getMyPayment() != null)
                    dto.setPayment(order.getMyPayment());

                return dto;
            })
            .collect(Collectors.toList());
    }

//    @Override
//    public OrderDTO createOrder(OrderDTO dto) {
//        Order order = modelMapper.map(dto, Order.class);
//
//        // Get User and Restaurant
//        User user = userDao.findById(dto.getCustomerId())
//            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
//        Restaurant restaurant = restaurantDao.findById(dto.getRestaurantId())
//            .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));
//
//        order.setCustomer(user);
//        order.setRestaurant(restaurant);
//
//        if (dto.getPayment() != null) {
//            Payment payment = dto.getPayment();
//            //payment.setOrder(order); // if bidirectional
//            order.setMyPayment(payment);
//        }
//        // Save order (cascades Payment if properly mapped)
//        orderDao.save(order);
//
//        return new ApiResponse("Order created successfully");
//    }
    
//    @Override
//    public OrderDTO createOrder(OrderDTO dto) {
//        // Map DTO to entity
//        Order order = modelMapper.map(dto, Order.class);
//
//        // Get User and Restaurant
//        User user = userDao.findById(dto.getCustomerId())
//            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
//        Restaurant restaurant = restaurantDao.findById(dto.getRestaurantId())
//            .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));
//
//        order.setCustomer(user);
//        order.setRestaurant(restaurant);
//
//        // Set default order status if not provided
//        if (order.getOrderStatus() == null) {
//            order.setOrderStatus(OrderStatus.NEW);
//        }
//
//        // Handle payment if provided
//        if (dto.getPayment() != null) {
//            Payment payment = dto.getPayment();
//            order.setMyPayment(payment); // Cascade will handle save
//        }
//
//        // Save order
//        Order savedOrder = orderDao.save(order);
//
//        // Map saved entity back to DTO to return ID & other details
//        OrderDTO savedOrderDTO = modelMapper.map(savedOrder, OrderDTO.class);
//        savedOrderDTO.setCustomerId(user.getId());
//        savedOrderDTO.setRestaurantId(restaurant.getId());
//
//        return savedOrderDTO;
//    }

    @Override
    public OrderDTO createOrder(OrderDTO dto) {
        // Map DTO to entity
        Order order = modelMapper.map(dto, Order.class);

        // Get User and Restaurant
        User user = userDao.findById(dto.getCustomerId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Restaurant restaurant = restaurantDao.findById(dto.getRestaurantId())
            .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        order.setCustomer(user);
        order.setRestaurant(restaurant);

        // Set default order status if not provided
        if (order.getOrderStatus() == null) {
            order.setOrderStatus(OrderStatus.NEW);
        }

        // Handle payment if provided
        if (dto.getPayment() != null) {
            Payment payment = dto.getPayment();
            order.setMyPayment(payment); // Cascade will handle save
        }

        // ✅ Fetch all cart items for this user (userId in Cart entity)
        List<Cart> userCarts = cartDao.findByUserId(user.getId());
        if (userCarts.isEmpty()) {
            throw new ResourceNotFoundException("Cart is empty for this user");
        }

        // ✅ Link carts to order and calculate total
        double totalAmount = 0;
        for (Cart cartItem : userCarts) {
            cartItem.setOrder(order); // This sets order_id in cart table
            totalAmount += cartItem.getSubTotal();
        }
        order.setCart(userCarts);
        order.setOrderAmount(totalAmount);

        // Save order (cascade will update cart table)
        Order savedOrder = orderDao.save(order);

        // Map saved entity back to DTO
        OrderDTO savedOrderDTO = modelMapper.map(savedOrder, OrderDTO.class);
        savedOrderDTO.setCustomerId(user.getId());
        savedOrderDTO.setRestaurantId(restaurant.getId());

        return savedOrderDTO;
    }

    

    @Override
    public ApiResponse updateOrder(Long id, OrderDTO dto) {
        Order order = orderDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        modelMapper.map(dto, order); // only direct fields

        orderDao.save(order);
        return new ApiResponse("Order updated successfully");
    }

    @Override
    public ApiResponse deleteOrder(Long id) {
        Order order = orderDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        orderDao.delete(order);
        return new ApiResponse("Order deleted successfully");
    }
    
    @Override
    public List<OrderDTO> getOrdersByStatus(OrderStatus status) {
        List<Order> orders = orderDao.findByOrderStatus(status);

        return orders.stream()
            .map(order -> {
                OrderDTO dto = modelMapper.map(order, OrderDTO.class);

                if (order.getCustomer() != null)
                    dto.setCustomerId(order.getCustomer().getId());

                if (order.getRestaurant() != null)
                    dto.setRestaurantId(order.getRestaurant().getId());

                if (order.getMyPayment() != null)
                    dto.setPayment(order.getMyPayment());

                return dto; 
            })
            .collect(Collectors.toList());
    }

    
    //payments and updates auto
 
    @Override
    public boolean getPaymentStatusForOrder(Long orderId) {
        return orderDao.findById(orderId)
            .orElseThrow(() -> new ApiException("Order not found with ID: " + orderId))
            .getMyPayment()
            .isStatus();
    }

    
////    private void updateDeliveryTimeIfLate(Order order) {
////        LocalDateTime expectedTime = order.getDeliveryDateTime();
////        LocalDateTime now = LocalDateTime.now();
////
////        if (now.isAfter(expectedTime)) {
////            order.setDeliveryDateTime(now); // set actual delivery time
////        }
////    }
//
//    private void updateOrderStatus(Order order) {
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime expectedDelivery = order.getDeliveryDateTime();
//
//        if (order.getOrderStatus() == OrderStatus.CANCELLED) {
//            // Already cancelled, do nothing
//            return;
//        }
//
//        if (order.getOrderStatus() == OrderStatus.NEW) {
//            // Move from NEW → PROCESSING
//            order.setOrderStatus(OrderStatus.PROCESSING);
//        }
//
//        if (order.getOrderStatus() == OrderStatus.PROCESSING) {
//            // Check if it's time to mark as delivered or pending payment
//            if (expectedDelivery.isBefore(now)) {
//                Payment payment = order.getMyPayment();
//
//                if (payment != null && (payment.getPaymentMethod() != PaymentMethod.CASH && payment.isStatus())) {
//                    // Non-cash payment and already paid
//                    order.setOrderStatus(OrderStatus.DELIVERED);
//                } else if (payment != null && payment.getPaymentMethod() == PaymentMethod.CASH) {
//                    // Cash on delivery → wait for confirmation
//                    order.setOrderStatus(OrderStatus.PENDING_PAYMENT);
//                }
//            }
//        }
//
//        if (order.getOrderStatus() == OrderStatus.PENDING_PAYMENT) {
//            // If cash payment was made, mark as delivered
//            Payment payment = order.getMyPayment();
//            if (payment != null && payment.isStatus()) {
//                order.setOrderStatus(OrderStatus.DELIVERED);
//            }
//        }
//    }
//    
//    public void autoUpdateOrder(Order order) {
//       // updateDeliveryTimeIfLate(order);
//        updateOrderStatus(order);
//        orderDao.save(order);
//    }


}

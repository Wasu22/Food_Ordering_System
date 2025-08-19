package com.cdac.service;

import java.util.List;

import com.cdac.dto.ApiResponse;
import com.cdac.dto.OrderDTO;
import com.cdac.entities.Order;
import com.cdac.entities.OrderStatus;

public interface OrderService {
    OrderDTO getOrderById(Long id);
    List<OrderDTO> getAllOrders();
    OrderDTO createOrder(OrderDTO dto);
    ApiResponse updateOrder(Long id, OrderDTO dto);
    ApiResponse deleteOrder(Long id);
    List<OrderDTO> getOrdersByStatus(OrderStatus status);
   // void autoUpdateOrder(Order order);
    boolean getPaymentStatusForOrder(Long orderId);

}

package com.cdac.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.cdac.entities.OrderStatus;
import com.cdac.entities.Payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {
    private Long id;
    private OrderStatus orderStatus;
    private double orderAmount;
    private LocalTime orderDateTime;
    private LocalDateTime deliveryDateTime;
    private int deliveryCharges;
    private Long customerId;
    private Long restaurantId;
   // private Long paymentId;
    private Payment payment;
   
}

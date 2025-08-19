package com.cdac.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.entities.Order;
import com.cdac.entities.OrderStatus;

public interface OrderDao extends JpaRepository<Order, Long> {
	List<Order> findByOrderStatus(OrderStatus status);
	Optional<Order> findById(Long id);  // Already exists via JpaRepository

}

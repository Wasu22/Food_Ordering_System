package com.cdac.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.entities.Cart;


public interface CartDao extends JpaRepository<Cart, Long> {
	
	 List<Cart> findByOrderId(Long orderId);

	List<Cart> findByUserId(Long id);
	

}
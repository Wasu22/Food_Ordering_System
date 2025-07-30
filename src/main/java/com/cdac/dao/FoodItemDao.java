package com.cdac.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.entities.FoodItem;

public interface FoodItemDao extends JpaRepository<FoodItem, Long> {

}

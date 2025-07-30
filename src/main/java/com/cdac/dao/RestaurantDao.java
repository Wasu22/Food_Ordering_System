package com.cdac.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cdac.entities.Restaurant;

public interface RestaurantDao extends JpaRepository<Restaurant, Long> {
 
	@Query("select r from Restaurant r left join fetch r.address where r.status = true")
	List<Restaurant> findByStatusTrueWithAddress();

	boolean existsByName(String restaurantName);
	
	//to fetch complete details(=restaurant + food items)
	@Query("select r from Restaurant r left join fetch r.address left join fetch r.foodItems where r.id=:restaurantId")
	Restaurant fetchCompleteDetails(Long restaurantId);
	
}

package com.cdac.entities;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "restaurants")
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true,exclude ="foodItems" )

public class Restaurant extends BaseEntity {
	@Column(length = 100, unique = true)
	private String name;
	@OneToOne(cascade = CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="address")
	private Address address;
	@Column(length = 20)
	private String description;
	@Enumerated(EnumType.STRING) 
	@Column(length = 30, name = "rating")
	private Rating rating;
	// for soft delete
	private boolean status;
	// Restaurant 1--->* FoodItem
	@OneToMany(mappedBy = "myRestaurant", 
			cascade = CascadeType.ALL, orphanRemoval = true)
	//@JsonIgnore
	private List<FoodItem> foodItems = new ArrayList<>();

	public Restaurant(String name, String description) {
		super();
		this.name = name;
		this.description = description;
		this.status = true;
	}

	// add helper method to add food item
	public void addFoodItem(FoodItem foodItem) {
		this.foodItems.add(foodItem);
		// add a link fooditem -> restaurant
		foodItem.setMyRestaurant(this);
	}

	// add helper method to remove food item
	public void removeFoodItem(FoodItem foodItem) {
		this.foodItems.remove(foodItem);
		// remove a link fooditem -> restaurant
		foodItem.setMyRestaurant(null);
	}
}


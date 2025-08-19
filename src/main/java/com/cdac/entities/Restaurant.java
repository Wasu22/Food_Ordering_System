package com.cdac.entities;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
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
@ToString(callSuper = true, exclude = {"foodItems", "reviews"})
public class Restaurant extends BaseEntity {

    @Column(length = 100, unique = true)
    private String name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address")
    private Address address;

    @Column(length = 300)
    private String description;

//    @Lob
//    @Column(name = "image", columnDefinition = "LONGBLOB") 
//    private byte[] image;

    private boolean status;

    @OneToMany(mappedBy = "myRestaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoodItem> foodItems = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    public Restaurant(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = true;
    }

    public void addFoodItem(FoodItem foodItem) {
        this.foodItems.add(foodItem);
        foodItem.setMyRestaurant(this);
    }

    public void removeFoodItem(FoodItem foodItem) {
        this.foodItems.remove(foodItem);
        foodItem.setMyRestaurant(null);
    }
}

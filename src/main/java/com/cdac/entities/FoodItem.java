package com.cdac.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "food_items", uniqueConstraints = @UniqueConstraint(columnNames = {"item_name", "restaurant_id"}))
@NoArgsConstructor
@ToString(callSuper = true, exclude = "myRestaurant")
@Getter
@Setter
public class FoodItem extends BaseEntity {

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_description", length = 200)
    private String itemDescription;

    @Column(name = "is_veg")
    private boolean isVeg;

    private int price;

//    @Lob
//    @Column(name = "image", columnDefinition = "LONGBLOB") 
//    private byte[] image;

    @Enumerated(EnumType.STRING)
    @Column(length = 30, name = "category")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant myRestaurant;

    public FoodItem(String itemName, String itemDescription, boolean isVeg, int price) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.isVeg = isVeg;
        this.price = price;
    }
}

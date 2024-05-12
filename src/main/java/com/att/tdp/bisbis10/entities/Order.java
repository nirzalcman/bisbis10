package com.att.tdp.bisbis10.entities;

import java.util.*;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "order_table")
public class Order {

    @Id
    private String id;

    @Column(name = "restaurant_id", insertable = false, updatable = false)
    private Long restaurantId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderItem> orderItems;//

    @ManyToOne
    @JoinColumn(name="restaurant_id", nullable=false)
    @JsonBackReference
    private Restaurant restaurant;


    public Order() {
            this.id = UUID.randomUUID().toString();
    }

    public Order(Long restaurantId , List<OrderItem> orderItems) {
        this.id = UUID.randomUUID().toString();
        this.restaurantId = restaurantId;
        this.orderItems = orderItems;
    }
    // getters and setters
    public String getId() {
        return id;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<OrderItem> getOrderItems() {
        return this.orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
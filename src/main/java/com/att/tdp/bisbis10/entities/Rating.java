package com.att.tdp.bisbis10.entities;

import jakarta.persistence.*;

@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long restaurantId;

    @Column(nullable = false)
    private Double rating;

    public Rating() {
    }

    public Rating(Long restaurantId, Double rating) {
        this.restaurantId = restaurantId;
        this.rating = rating;
    }

    public Long getId() {
        return this.id;
    }

    public Long getRestaurantId() {
        return this.restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Double getRating() {
        return this.rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
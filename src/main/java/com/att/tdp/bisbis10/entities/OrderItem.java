package com.att.tdp.bisbis10.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long dishId;

    @Column(nullable = false)
    private Long amount;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;  // Reference to the Order entity


    public OrderItem() {
    }

    public OrderItem(Long dishId, Long amount) {
        this.dishId = dishId;
        this.amount = amount;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDishId() {
        return this.dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    public Long getAmount() {
        return this.amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}

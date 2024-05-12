package com.att.tdp.bisbis10.controller;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.att.tdp.bisbis10.entities.Order;
import com.att.tdp.bisbis10.services.OrderService;

import ch.qos.logback.core.joran.sanity.Pair;

@RestController
public class OrderControler {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<SimpleEntry<String, String>> addOrder(@RequestBody Order order) {
        Order added_order = orderService.addOrder(order);
        return ResponseEntity.ok(new SimpleEntry<String, String>("order_id", added_order.getId()));
    }
}

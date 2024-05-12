package com.att.tdp.bisbis10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.att.tdp.bisbis10.entities.Rating;
import com.att.tdp.bisbis10.services.RatingService;

@RestController
public class RatingControler {
    
    @Autowired
    private RatingService ratingService;

    @PostMapping("/ratings")
    public ResponseEntity<Void> addRating(@RequestBody Rating rating) {
        ratingService.addRating(rating);
        return ResponseEntity.ok().build();

    }
}

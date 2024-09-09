package com.practice.controllers;

import com.practice.dtos.RatingDto;
import com.practice.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rating")
@CrossOrigin(origins = "http://localhost:4200/")

public class RatingController {
    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/addRating")
    public ResponseEntity<RatingDto> addRating(@RequestBody RatingDto ratingDto) {
        return new ResponseEntity<>(ratingService.createRating(ratingDto), HttpStatus.CREATED);
    }

    @GetMapping("/getAllRatings")
    public ResponseEntity<List<RatingDto>> getAllRatings(@RequestParam(name = "pageSize", defaultValue = "3", required = false) int pageSize,
                                                         @RequestParam(name = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                         @RequestParam(name = "sortBy", defaultValue = "ratingId", required = false) String sortBy) {
        return new ResponseEntity<>(ratingService.getAllRatings(pageSize, pageNumber, sortBy), HttpStatus.OK);
    }

    @GetMapping("/getByUser/{userId}")
    public ResponseEntity<List<RatingDto>> getRatingByUser(@PathVariable String userId) {
        return new ResponseEntity<>(ratingService.getRatingByUser(userId), HttpStatus.OK);
    }

    @GetMapping("/getByHotel/{hotelId}")
    public ResponseEntity<List<RatingDto>> getRatingByHotel(@PathVariable String hotelId) {
        return new ResponseEntity<>(ratingService.getRatingByHotel(hotelId), HttpStatus.OK);
    }
}

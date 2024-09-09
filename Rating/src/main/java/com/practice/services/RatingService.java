package com.practice.services;

import com.practice.dtos.RatingDto;

import java.util.List;

public interface RatingService {
    RatingDto createRating(RatingDto ratingDto);
    List<RatingDto> getAllRatings(int pageSize, int pageNumber,String sortBy);
    List<RatingDto> getRatingByUser(String userId);
    List<RatingDto> getRatingByHotel(String hotelId);

}

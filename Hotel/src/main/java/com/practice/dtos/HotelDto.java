package com.practice.dtos;

import lombok.Data;

import java.util.List;

@Data
public class HotelDto {
    private String hotelId;
    private String userId;
    private String name;
    private String location;
    private String about;
    private List<Rating> ratings;
}

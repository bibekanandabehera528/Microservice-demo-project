package com.practice.dtos;

import lombok.Data;

import java.util.List;

@Data
public class Hotels {
    private String hotelId;
    private String name;
    private String location;
    private String about;
    private List<Rating> ratings;
}

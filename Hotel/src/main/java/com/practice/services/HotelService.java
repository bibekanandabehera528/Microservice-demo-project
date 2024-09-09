package com.practice.services;

import com.practice.dtos.HotelDto;

import java.util.List;

public interface HotelService {
    HotelDto addHotel(HotelDto hotelDto);
    List<HotelDto> getAllHotels(int pageSize,int pageNumber, String sortBy);
    HotelDto getHotelById(String hotelId);
    List<HotelDto> getHotelsByUser(String userId);
}
package com.practice.services.impl;

import com.practice.dtos.HotelDto;
import com.practice.dtos.Rating;
import com.practice.entities.Hotels;
import com.practice.exceptions.ResourceNotFoundException;
import com.practice.repos.HotelRepo;
import com.practice.services.HotelService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {
    private final HotelRepo hotelRepo;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;
    @Autowired
    public HotelServiceImpl(HotelRepo hotelRepo, ModelMapper modelMapper, RestTemplate restTemplate) {
        this.modelMapper = modelMapper;
        this.hotelRepo = hotelRepo;
        this.restTemplate = restTemplate;
    }

    @Override
    public HotelDto addHotel(HotelDto hotelDto) {
        Hotels hotels = modelMapper.map(hotelDto, Hotels.class);
        hotels.setHotelId(UUID.randomUUID().toString());
        return modelMapper.map(hotelRepo.save(hotels), HotelDto.class);
    }

    @Override
    public List<HotelDto> getAllHotels(int pageSize, int pageNumber, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        return hotelRepo.findAll(pageable).stream().map(i -> modelMapper.map(i, HotelDto.class)).collect(Collectors.toList());
    }

    @Override
    public HotelDto getHotelById(String hotelId) {
        Hotels hotels = hotelRepo.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel not found !!!"));
//        List <Rating> ratings = restTemplate.getForObject("http://localhost:8083/rating/getByHotel/"+hotels.getHotelId(), ArrayList.class);
        List<Rating> ratings = restTemplate.exchange(
                "http://RATING/rating/getByHotel/" + hotels.getHotelId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Rating>>() {}
        ).getBody();
        hotels.setRatings(ratings);
        return modelMapper.map(hotels, HotelDto.class);
    }

    @Override
    public List<HotelDto> getHotelsByUser(String userId) {
        List<Hotels> hotels = hotelRepo.getHotelsByUser(userId);
        hotels.forEach(i -> {
            List<Rating> ratings = restTemplate.exchange(
                    "http://RATING/rating/getByHotel/"+i.getHotelId(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Rating>>() {}
            ).getBody();
            i.setRatings(ratings);
        });

        return hotels.stream().map(i -> modelMapper.map(i,HotelDto.class)).collect(Collectors.toList());
    }
}

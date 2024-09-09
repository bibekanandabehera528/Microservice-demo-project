package com.practice.services.impl;

import com.practice.dtos.RatingDto;
import com.practice.entities.Rating;
import com.practice.repos.RatingRepo;
import com.practice.services.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
public class RatingServiceImpl implements RatingService {
    private final ModelMapper modelMapper;
    private final RatingRepo ratingRepo;

    @Autowired
    public RatingServiceImpl(ModelMapper modelMapper, RatingRepo ratingRepo) {
        this.modelMapper = modelMapper;
        this.ratingRepo = ratingRepo;
    }

    @Override
    public RatingDto createRating(RatingDto ratingDto) {
        ratingDto.setRatingId(UUID.randomUUID().toString());
        return modelMapper.map(ratingRepo.save(modelMapper.map(ratingDto, Rating.class)), RatingDto.class);
    }

    @Override
    public List<RatingDto> getAllRatings(int pageSize, int pageNumber, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize,Sort.by(sortBy).ascending());
        return ratingRepo.findAll(pageable).get().map(i -> modelMapper.map(i, RatingDto.class)).collect(Collectors.toList());

    }

    @Override
    public List<RatingDto> getRatingByUser(String userId) {
        return ratingRepo.getRatingsByUser(userId).stream().map(i -> modelMapper.map(i, RatingDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<RatingDto> getRatingByHotel(String hotelId) {
        return ratingRepo.getRatingsByHotel(hotelId).stream().map(i -> modelMapper.map(i, RatingDto.class)).collect(Collectors.toList());
    }
}

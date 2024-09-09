package com.practice.controllers;

import com.practice.dtos.HotelDto;
import com.practice.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel")
@CrossOrigin(origins = "http://localhost:4200/")
public class HotelController {
    private final HotelService hotelService;
    @Autowired
    public HotelController(HotelService hotelService){
        this.hotelService = hotelService;
    }
    @PostMapping("/addHotel")
    public ResponseEntity<HotelDto> addHotel(@RequestBody HotelDto hotelDto){
        return new ResponseEntity<>(hotelService.addHotel(hotelDto), HttpStatus.CREATED);
    }

    @GetMapping("/getHotelById/{hotelId}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable String hotelId){
        return new ResponseEntity<>(hotelService.getHotelById(hotelId), HttpStatus.OK);
    }

    @GetMapping("/getHotelsByUserId/{userId}")
    public ResponseEntity<List<HotelDto>> getAllHotelsByUserId(@PathVariable String userId){
        return new ResponseEntity<>(hotelService.getHotelsByUser(userId),HttpStatus.OK);
    }

    @GetMapping("/getAllHotels")
    public ResponseEntity<List<HotelDto>> getAllHotels(@RequestParam(name = "pageSize",defaultValue = "3",required = false) int pageSize,
                                                       @RequestParam(name = "pageNumber",defaultValue = "0",required = false) int pageNumber,
                                                       @RequestParam(name = "sortBy",defaultValue = "hotelId",required = false) String sortBy){
        return new ResponseEntity<>(hotelService.getAllHotels(pageSize,pageNumber,sortBy), HttpStatus.OK);
    }
}

package com.practice.services.impl;

import com.practice.dtos.Hotels;
import com.practice.dtos.UserDto;
import com.practice.entities.User;
import com.practice.exceptions.ResourceNotFoundException;
import com.practice.repos.UserRepo;
import com.practice.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Value("${user.image.path}")
    private String imagePath;
    private final ModelMapper modelMapper;
    private final UserRepo userRepo;
    private final RestTemplate restTemplate;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, UserRepo userRepo, RestTemplate restTemplate) {
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
        this.restTemplate = restTemplate;
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        User user = modelMapper.map(userDto, User.class);
        return modelMapper.map(userRepo.save(user), UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers(int pageSize, int pageNumber, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        return userRepo.findAll(pageable).stream().map(i -> modelMapper.map(i, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public void uploadProfilePic(MultipartFile multipartFile, String userId) {
        String imageName = UUID.randomUUID().toString().concat(multipartFile.getOriginalFilename());
        try {
            multipartFile.transferTo(new File(imagePath+imageName));
        }catch(IOException io){}
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found !!!"));
        user.setProfilePic(imageName);
        userRepo.save(user);
    }

    @Override
    public InputStream displayProfileImage(String userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found !!!"));
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(imagePath + user.getProfilePic());
        }catch(FileNotFoundException f){}
        return fileInputStream;
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found !!!"));

        userRepo.delete(user);
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found !!!"));
        List<Hotels> hotels = restTemplate.exchange(
                "http://HOTEL/hotel/getHotelsByUserId/"+user.getUserId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Hotels>>() {
                }
        ).getBody();
        user.setHotels(hotels);
        return modelMapper.map(user,UserDto.class);
    }
}
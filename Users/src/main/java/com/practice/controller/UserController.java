package com.practice.controller;

import com.practice.dtos.UserDto;
import com.practice.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/addUser")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.addUser(userDto), HttpStatus.CREATED);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(name = "pageSize",defaultValue = "100",required = false) int pageSize,
                                                     @RequestParam(name = "pageNumber",defaultValue = "0",required = false) int pageNumber,
                                                     @RequestParam(name = "sortBy", defaultValue = "userId",required = false) String sortBy) {
        return new ResponseEntity<>(userService.getAllUsers(pageSize, pageNumber, sortBy), HttpStatus.OK);
    }

    @PostMapping("/uploadImage/{userId}")
    public ResponseEntity<?> uploadProfilePic(@PathVariable String userId, MultipartFile profilePic){
        userService.uploadProfilePic(profilePic,userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/dispProfilePic/{userId}")
    public ResponseEntity<?> getProfilePic(@PathVariable String userId, HttpServletResponse response) throws IOException {
        InputStream inputStream = userService.displayProfileImage(userId);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(inputStream,response.getOutputStream());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getUserById/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId){
        return new ResponseEntity<>(userService.getUserById(userId),HttpStatus.OK);
    }
}

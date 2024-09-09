package com.practice.services;

import com.practice.dtos.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface UserService {
    UserDto addUser(UserDto userDto);
    List<UserDto> getAllUsers(int pageSize,int pageNumber,String sortBy);
    void uploadProfilePic(MultipartFile multipartFile, String userId);

    InputStream displayProfileImage(String userId);

    void deleteUser(String userId);

    UserDto getUserById(String userId);
}

package com.practice.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserDto {
    private String userId;
    private String name;
    private String gender;
    private int age;
    private String profilePic;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dob;
    private List<Hotels> hotels;
}

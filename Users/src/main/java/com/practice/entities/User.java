package com.practice.entities;

import com.practice.dtos.Hotels;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    private String userId;
    private String name;
    private String gender;
    private int age;
    private String profilePic;
    private LocalDate dob;
    @Transient
    private List<Hotels> hotels;
}

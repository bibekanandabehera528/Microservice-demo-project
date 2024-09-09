package com.practice.entities;

import com.practice.dtos.Rating;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Hotels {
    @Id
    private String hotelId;
    private String userId;
    private String name;
    private String location;
    private String about;
    @Transient
    private List<Rating> ratings;
}

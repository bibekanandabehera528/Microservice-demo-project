package com.practice.repos;

import com.practice.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepo extends JpaRepository<Rating,String> {
    @Query(value = "select * from rating where user_id = :userId",nativeQuery = true)
    List<Rating> getRatingsByUser(@Param("userId") String userId);

    @Query(value = "select * from rating where hotel_id = :hotelId",nativeQuery = true)
    List<Rating> getRatingsByHotel(@Param("hotelId") String hotelId);
}

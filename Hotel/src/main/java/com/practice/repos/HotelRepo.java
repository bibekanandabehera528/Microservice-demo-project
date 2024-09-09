package com.practice.repos;

import com.practice.entities.Hotels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HotelRepo extends JpaRepository<Hotels,String> {
    @Query(value = "select * from hotels where user_id =:userId",nativeQuery = true)
    List<Hotels> getHotelsByUser(@Param("userId") String userId);
}

package com.backend.wanderlog.repository;

import com.backend.wanderlog.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel,Long> {
    List<Hotel> findHotelsByTravelDestinationId(long TravelDestinationId);


    //boolean existsByCodeStudent(String codeStudent);
    //boolean existsByCodeStudentAndBookAndBookLoan(String codeStudent, TouristicAttraction touristicAttraction, boolean bookLoan) ;
    //List<Hotel> findByCodeStudent(String codeStudent);
}

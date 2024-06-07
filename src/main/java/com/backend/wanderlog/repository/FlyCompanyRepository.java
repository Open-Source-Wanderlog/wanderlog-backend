package com.backend.wanderlog.repository;

import com.backend.wanderlog.model.FlyCompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlyCompanyRepository extends JpaRepository<FlyCompany,Long> {
    List<FlyCompany> findFlyCompaniesByTravelDestinationId(long TravelDestinationId);


    //boolean existsByCodeStudent(String codeStudent);
    //boolean existsByCodeStudentAndBookAndBookLoan(String codeStudent, TouristicAttraction touristicAttraction, boolean bookLoan) ;
    //List<Hotel> findByCodeStudent(String codeStudent);
}

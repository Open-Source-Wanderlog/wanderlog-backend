package com.backend.wanderlog.repository;

import com.backend.wanderlog.model.TravelDestination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelDestinationRepository extends JpaRepository<TravelDestination, Long> {
    //@Query("SELECT r FROM Payment r WHERE r.employee.dni=:dni")
    //List<Payment> findRemunerationsByEmployeeDni(@Param("dni")String dni);

    //@Query("SELECT r FROM Payment r WHERE r.createDate BETWEEN :startDate AND :endDate")
    //List<Payment> findRemunerationsByCreateDateRange(@Param("startDate") LocalDate startDate,
    //                                                 @Param("endDate") LocalDate endDate);

    List<TravelDestination> findTravelDestinationsByNameIsLikeIgnoreCase(String name);
}

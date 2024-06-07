package com.backend.wanderlog.repository;

import com.backend.wanderlog.model.TravelPlans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TravelPlansRepository extends JpaRepository<TravelPlans,Long> {
    //@Query("SELECT t FROM Plan t WHERE t.account.nameCustomer=:nameCustomer")
    //List<Plan> findByNameCustomer(@Param("nameCustomer")String customer);


    //@Query("SELECT t FROM Plan t WHERE t.createDate BETWEEN :startDate AND :endDate")
    //List<Plan> findTransactionByCreateDateRange(@Param("startDate") LocalDate startDate,
    //                                            @Param("endDate") LocalDate endDate);

    @Query("SELECT t FROM TravelPlans t WHERE t.user.id=:userId")
    List<TravelPlans> findByUserId(@Param("userId") long userId);
}

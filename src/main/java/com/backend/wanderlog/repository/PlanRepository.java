package com.backend.wanderlog.repository;

import com.backend.wanderlog.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    //Usando Query Method (Keywords)
    boolean existsByName(String name);
    boolean existsByPrice(float price);
}

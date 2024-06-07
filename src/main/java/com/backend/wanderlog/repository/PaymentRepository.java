package com.backend.wanderlog.repository;

import com.backend.wanderlog.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByUserId(Long id);
    @Query("SELECT r FROM Payment r WHERE r.startDate < :date AND :date < r.endDate")
    List<Payment> findPaymentForActualDate(@Param("date") LocalDate date);

}

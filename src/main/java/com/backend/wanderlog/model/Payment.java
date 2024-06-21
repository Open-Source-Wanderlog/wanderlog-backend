package com.backend.wanderlog.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="plan_payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name="plan_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_PAYMENT_PLAN_ID"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Payment payment;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_PAYMENT_USER_ID"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;
}

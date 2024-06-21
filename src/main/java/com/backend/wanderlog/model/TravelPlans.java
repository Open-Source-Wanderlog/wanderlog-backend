package com.backend.wanderlog.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="travel_plans")
public class TravelPlans {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long travelPlanId;
    @ManyToOne
    @JoinColumn(name="hotel_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_HOTEL_TRAVEL_PLAN"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_USER_TRAVEL_PLAN"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    @ManyToOne
    @JoinColumn(name="fly_company_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_FLY_COMPANY_TRAVEL_PLAN"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private FlyCompany flyCompany;

    @ManyToMany
    @JoinTable(
            name = "travel_plan_touristic_attraction",
            joinColumns = @JoinColumn(name = "travel_plan_id"),
            inverseJoinColumns = @JoinColumn(name = "touristic_attraction_id"))
    private Set<TouristicAttraction> touristicAttractions;
}

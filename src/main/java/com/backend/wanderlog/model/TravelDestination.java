package com.backend.wanderlog.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="travel_destinations")
public class TravelDestination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",length = 255, nullable = false)
    private String name;

    @Column(name = "description",length = 5000, nullable = false)
    private String description;

    @Column(name = "image_url",length = 255, nullable = false)
    private String image_url;
}

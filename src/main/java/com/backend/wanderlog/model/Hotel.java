package com.backend.wanderlog.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name="hotels")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",length = 255, nullable = false)
    private String name;

    @Column(name = "description",length = 255, nullable = false)
    private String description;

    @Column(name = "image_url",length = 255, nullable = false)
    private String imageUrl;

    @Column(name = "price", nullable = false)
    private float price;

    @ManyToOne
    @JoinColumn(name="travel_destination_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_HOTEL_TRAVEL_DESTINATION_ID"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private TravelDestination travelDestination;
}

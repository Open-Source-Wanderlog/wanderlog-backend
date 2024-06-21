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
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email",length = 255, nullable = false)
    private String email;

    @Column(name = "password",length = 255, nullable = false)
    private String password;

    @Column(name = "full_name",length = 255, nullable = false)
    private String fullName;

    @Column(name = "phone",length = 255, nullable = false)
    private String phone;

    @ManyToOne
    @JoinColumn(name="subscriptions_id", nullable = true,
            foreignKey = @ForeignKey(name = "FK_SUBSCRIPTION_ID"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Plan plan;
}

package com.backend.wanderlog.repository;

import com.backend.wanderlog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmailAndPassword(String email, String password);
    User getUserByEmailAndPassword(String email, String password);
}

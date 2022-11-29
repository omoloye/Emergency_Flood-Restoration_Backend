package com.cleaning.cleaningbackend.repository;

import com.cleaning.cleaningbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);

    Boolean existsUserByFirstName(String firstName);
    User findUserByEmail(String email);
    Optional<User> findByEmail(String email);
}

package com.cleaning.cleaningbackend.repository;

import com.cleaning.cleaningbackend.models.Cleaner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CleanerRepository extends JpaRepository<Cleaner, Long> {

    Cleaner findCleanerByEngagedStatus(Boolean value);

    @Override
    long count();
}

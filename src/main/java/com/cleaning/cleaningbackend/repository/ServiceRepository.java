package com.cleaning.cleaningbackend.repository;

import com.cleaning.cleaningbackend.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service,Long> {

    Service findServiceById(Long id);
    Boolean existsServiceById(Long id);
}

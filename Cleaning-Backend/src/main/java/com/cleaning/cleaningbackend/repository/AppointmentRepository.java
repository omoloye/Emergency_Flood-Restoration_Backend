package com.cleaning.cleaningbackend.repository;

import com.cleaning.cleaningbackend.models.Appointment;
import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}

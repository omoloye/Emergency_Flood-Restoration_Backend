package com.cleaning.cleaningbackend.services;

import com.cleaning.cleaningbackend.dto.AppointmentDto;
import com.cleaning.cleaningbackend.dto.AppointmentResponse;
import com.cleaning.cleaningbackend.models.Cleaner;

import java.util.List;

public interface UserService {
   AppointmentResponse saveAppointment(AppointmentDto appointmentDto);
   Cleaner availableCleaners(AppointmentDto appointmentDto);

   List<Cleaner> listAllCleaners();
   int countAllCleaners();

}

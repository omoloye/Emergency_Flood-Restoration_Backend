package com.cleaning.cleaningbackend.services.impl;

import com.cleaning.cleaningbackend.dto.AppointmentDto;
import com.cleaning.cleaningbackend.dto.AppointmentResponse;
import com.cleaning.cleaningbackend.exception.ResourceNotFoundException;
import com.cleaning.cleaningbackend.models.Appointment;
import com.cleaning.cleaningbackend.models.Cleaner;
import com.cleaning.cleaningbackend.models.Service;
import com.cleaning.cleaningbackend.models.User;
import com.cleaning.cleaningbackend.models.enums.AppointmentStatus;
import com.cleaning.cleaningbackend.repository.AppointmentRepository;
import com.cleaning.cleaningbackend.repository.CleanerRepository;
import com.cleaning.cleaningbackend.repository.ServiceRepository;
import com.cleaning.cleaningbackend.repository.UserRepository;
import com.cleaning.cleaningbackend.services.UserService;


import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@org.springframework.stereotype.Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CleanerRepository cleanerRepository;
    private final AppointmentRepository appointmentRepository;
    private final ServiceRepository serviceRepository;

    public UserServiceImpl(UserRepository userRepository, CleanerRepository cleanerRepository, AppointmentRepository appointmentRepository, ServiceRepository serviceRepository) {
        this.userRepository = userRepository;
        this.cleanerRepository = cleanerRepository;
        this.appointmentRepository = appointmentRepository;
        this.serviceRepository = serviceRepository;
    }

    @Override
    public AppointmentResponse saveAppointment(AppointmentDto appointmentDto) {
        Boolean existsByEmail = userRepository.existsByEmail(appointmentDto.getEmail());
        if (existsByEmail) {
            throw new ResourceNotFoundException("email exist already");
        }
        User user = new User();
        user.setFirstName(appointmentDto.getFirstName());
        user.setLastName(appointmentDto.getLastName());
        user.setEmail(appointmentDto.getEmail());
        user.setNumber(appointmentDto.getNumber());
        user.setAddress(appointmentDto.getAddress());
        userRepository.save(user);
        Appointment appointment = new Appointment();
        appointment.setDateOfAppointment(Date.valueOf(appointmentDto.getDate()));
        appointment.setUser(user);
        Set<Service> services = appointmentDto.getServices().stream().map(id -> serviceRepository.findServiceById(Long.getLong(id))).collect(Collectors.toSet());
        appointment.setServiceList(services);
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setTitle(appointmentDto.getFirstName() + " Appointment");
        appointmentRepository.save(appointment);
        AppointmentResponse appointmentResponse = new AppointmentResponse();
        if(checkForAvailableCleaner()){
            appointment.setStatus(AppointmentStatus.AWAITING_PAYMENT);
            appointmentResponse.setMessage("Kindly pay for service and after confirmation, a cleaner will contact you");
            // show the client a message has been sent to his email of the payment details and a cleaner will reach out after
            // payment has been confirmed.
        }else{
            appointmentResponse.setMessage("All cleaners are busy at the moment, we will send a mail informing you when one is available.");
        }
        return appointmentResponse;
    }

    @Override
    public Cleaner availableCleaners(AppointmentDto appointmentDto) {
        List<Cleaner> cleanersList = cleanerRepository.findAll();
        return cleanersList.stream().filter(cleaner -> !cleaner.getEngagedStatus()).findFirst().orElseThrow(() -> new ResourceNotFoundException("cleaners not available"));
    }

    @Override
    public List<Cleaner> listAllCleaners() {
        return cleanerRepository.findAll();
    }

    @Override
    public int countAllCleaners() {
        return (int) cleanerRepository.count();
    }

    private boolean checkForAvailableCleaner(){
       return cleanerRepository.findAll().stream().anyMatch(cleaner -> cleaner.getEngagedStatus() == false);
    }

}

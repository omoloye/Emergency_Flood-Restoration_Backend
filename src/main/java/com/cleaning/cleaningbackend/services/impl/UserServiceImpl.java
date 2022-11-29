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
import com.cleaning.cleaningbackend.services.EmailService;
import com.cleaning.cleaningbackend.services.UserService;


import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CleanerRepository cleanerRepository;
    private final AppointmentRepository appointmentRepository;
    private final ServiceRepository serviceRepository;

    private final EmailService emailService;

    public UserServiceImpl(UserRepository userRepository, CleanerRepository cleanerRepository, AppointmentRepository appointmentRepository, ServiceRepository serviceRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.cleanerRepository = cleanerRepository;
        this.appointmentRepository = appointmentRepository;
        this.serviceRepository = serviceRepository;
        this.emailService = emailService;
    }


    @Override
    public AppointmentResponse saveAppointment(AppointmentDto appointmentDto) {
        System.out.println(appointmentDto);
        Boolean existsByEmail = userRepository.existsByEmail(appointmentDto.getEmail());
        Boolean existByFirstName = userRepository.existsUserByFirstName(appointmentDto.getFirstName());
        if (existsByEmail && existByFirstName) {
            System.out.println("in the exception");
            throw new ResourceNotFoundException("email exist already");
        } else {
            User user = new User();
            user.setFirstName(appointmentDto.getFirstName());
            user.setLastName(appointmentDto.getLastName());
            user.setEmail(appointmentDto.getEmail());
            user.setNumber(appointmentDto.getNumber());
            user.setAddress(appointmentDto.getAddress());
            userRepository.save(user);
            System.out.println("saved a user");
            Appointment appointment = new Appointment();
            appointment.setDateOfAppointment(Date.valueOf(appointmentDto.getDate()));
            appointment.setUser(user);
            System.out.println(appointmentDto.getServices());
            String idsStr = appointmentDto.getServices();
           List<String> ids =  Arrays.asList(idsStr.split(","));
            System.out.println(ids);
            Set<Service> services = ids.stream().map(id -> serviceRepository.findServiceById(Long.parseLong(id))).collect(Collectors.toSet());
            System.out.println(services);
            appointment.setServiceList(services);
            appointment.setStatus(AppointmentStatus.PENDING);
            appointment.setTitle(appointmentDto.getFirstName() + " Appointment");
            appointmentRepository.save(appointment);
            System.out.println("saved an appointment");
            AppointmentResponse appointmentResponse = new AppointmentResponse();
            if (checkForAvailableCleaner() && availableCleaners() != null) {
                Cleaner cleaner = availableCleaners();
                cleaner.setEngagedStatus(true);
                cleanerRepository.save(cleaner);
                appointment.setCleaner(cleaner);
                appointment.setStatus(AppointmentStatus.AWAITING_PAYMENT);
                System.out.println(appointment);
                appointmentResponse.setMessage("Kindly pay for service and after confirmation, a cleaner will contact you");
                emailService.sendSimpleMail("", user.getEmail(), "Appointment","Kindly pay To this account number: 10000000 Bank name: Eco bank to validate your booking after which are cleaner would contact you");
                // show the client a message has been sent to his email of the payment details and a cleaner will reach out after
                // payment has been confirmed.
            } else {
                appointmentResponse.setMessage("All cleaners are busy at the moment, we will send a mail informing you when one is available.");
            }
            return appointmentResponse;
        }
    }

    @Override
    public Cleaner availableCleaners() {
        List<Cleaner> cleanersList = cleanerRepository.findAll();
        return cleanersList.stream().filter(cleaner -> !cleaner.getEngagedStatus()).findAny().orElseThrow(() -> new ResourceNotFoundException("cleaners not available"));
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

package com.cleaning.cleaningbackend.controllers;

import com.cleaning.cleaningbackend.dto.AppointmentDto;
import com.cleaning.cleaningbackend.dto.AppointmentResponse;
import com.cleaning.cleaningbackend.repository.UserRepository;
import com.cleaning.cleaningbackend.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8080/" )
@RestController
@RequestMapping( value = "/api/users")
public class UserController {

    private final UserService userService;

    public UserController( UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/saveuser")
    public ResponseEntity<AppointmentResponse>  saveUserAndAppointment(@RequestBody AppointmentDto appointmentDto){
        userService.saveAppointment(appointmentDto);
        return new ResponseEntity<>(userService.saveAppointment(appointmentDto), HttpStatus.OK);
    }




}

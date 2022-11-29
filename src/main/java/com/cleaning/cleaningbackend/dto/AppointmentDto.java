package com.cleaning.cleaningbackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.List;

@Getter
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {
    private String firstName;
    private String lastName;
    private String email;
    private String number;
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private String services;
    private String date;
    private String address;

}

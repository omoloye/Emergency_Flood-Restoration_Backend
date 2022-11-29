package com.cleaning.cleaningbackend.models;

import com.cleaning.cleaningbackend.models.enums.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "appointment")

public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private String title;

    private Date dateOfAppointment;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "user_id")
    private User user;

    @OneToMany(mappedBy = "appointment")
    private Set<Service> serviceList;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cleaner_id")
    private Cleaner cleaner;

}

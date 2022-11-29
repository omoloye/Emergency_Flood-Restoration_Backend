package com.cleaning.cleaningbackend.models;

import com.cleaning.cleaningbackend.models.enums.Role;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    private String firstName;
    private String lastName;
    private String email;

    private String password;
    private String address;
    private String number;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Appointment> appointments;

    public User( String firstName, String lastName, String email, String address, String number) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.number = number;
    }

    public User(String firstName, String lastName, String email, String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}

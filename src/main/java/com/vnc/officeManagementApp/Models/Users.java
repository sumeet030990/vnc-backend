package com.vnc.officeManagementApp.Models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.context.annotation.Lazy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String companyName;
    private String contactNumber;
    private String address;

    // commision ll mean rs/quintal for buyer and seller,
    // for transporter it ll be amount, default null
    private int commision;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @Lazy
    private Roles roles;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties({ "users" }) // Prevent infinite recursion during serialization
    @Lazy
    private UserAuth userAuth;

    @Lazy
    @OneToOne(mappedBy = "users")
    private UserBalance userBalance;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @UpdateTimestamp
    private LocalDateTime updatedDateTime;

}

package com.vnc.officeManagementApp.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String firstName;
    private String lastName;
    private String contactNumber;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Roles roles;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore  // Prevent infinite recursion during serialization
    private UserAuth userAuth;

}

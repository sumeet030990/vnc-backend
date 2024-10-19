package com.vnc.officeManagementApp.Models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UserAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String password;
    private Boolean isEnabled;

    @OneToOne
    private Users users;
}

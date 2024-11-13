package com.vnc.officeManagementApp.Seeders;

import com.vnc.officeManagementApp.Models.Roles;
import com.vnc.officeManagementApp.Repository.RolesRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RolesSeeder {
    @Bean
    CommandLineRunner initRoleSeeder(RolesRepository rolesRepository) {
        return args -> {
            rolesRepository.save(new Roles(1, "admin", "ADMIN"));
            rolesRepository.save(new Roles(2, "transporter", "TRANSPORTER"));
            rolesRepository.save(new Roles(3, "seller", "SELLER"));
            rolesRepository.save(new Roles(4, "buyer", "BUYER"));
        };
    }
}

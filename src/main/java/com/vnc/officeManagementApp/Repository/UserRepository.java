package com.vnc.officeManagementApp.Repository;

import com.vnc.officeManagementApp.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}

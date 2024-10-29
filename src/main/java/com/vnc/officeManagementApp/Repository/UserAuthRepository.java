package com.vnc.officeManagementApp.Repository;

import com.vnc.officeManagementApp.Models.UserAuth;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthRepository extends JpaRepository<UserAuth,Long> {
    Optional<UserAuth> findByUserName(String user_name);
}

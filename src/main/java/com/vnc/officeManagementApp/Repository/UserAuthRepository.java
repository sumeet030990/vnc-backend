package com.vnc.officeManagementApp.Repository;

import com.vnc.officeManagementApp.Models.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthRepository extends JpaRepository<UserAuth,Long> {
    UserAuth findByUserName(String user_name);
}

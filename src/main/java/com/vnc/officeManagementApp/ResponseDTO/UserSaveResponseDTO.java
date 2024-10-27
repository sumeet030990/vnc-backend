package com.vnc.officeManagementApp.ResponseDTO;

import com.vnc.officeManagementApp.Models.UserAuth;
import com.vnc.officeManagementApp.Models.Users;
import lombok.Data;

@Data
public class UserSaveResponseDTO {
    private UserAuth userAuth;
    private Users users;
    private String jwtToken;

    public UserSaveResponseDTO(UserAuth userAuth, Users users, String jwtToken) {
        this.userAuth = userAuth;
        this.users = users;
        this.jwtToken = jwtToken;
    }
}

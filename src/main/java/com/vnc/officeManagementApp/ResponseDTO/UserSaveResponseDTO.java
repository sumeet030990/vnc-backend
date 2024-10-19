package com.vnc.officeManagementApp.ResponseDTO;

import com.vnc.officeManagementApp.Models.UserAuth;
import com.vnc.officeManagementApp.Models.Users;
import lombok.Data;

@Data
public class UserSaveResponseDTO {
    private UserAuth userAuth;
    private Users users;

    public UserSaveResponseDTO(UserAuth userAuth, Users users) {
        this.userAuth = userAuth;
        this.users = users;
    }
}

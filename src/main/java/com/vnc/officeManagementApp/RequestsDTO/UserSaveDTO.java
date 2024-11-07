package com.vnc.officeManagementApp.RequestsDTO;

import lombok.Data;

@Data
public class UserSaveDTO {
    private Long userId = null;
    private Long userAuthId = null;
    private String userName;
    private String password;
    private Long roleId;
    private String firstName;
    private String lastName;
    private String contactNumber;
    private String address;

}

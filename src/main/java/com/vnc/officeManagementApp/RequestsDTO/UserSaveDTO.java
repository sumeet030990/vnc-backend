package com.vnc.officeManagementApp.RequestsDTO;

import lombok.Data;

@Data
public class UserSaveDTO {
    private String userName;
    private String password;
    private Long roleId;
    private String firstName;
    private String lastName;
    private String contactNumber;

}

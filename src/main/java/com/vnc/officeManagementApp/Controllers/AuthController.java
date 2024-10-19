package com.vnc.officeManagementApp.Controllers;

import com.vnc.officeManagementApp.Models.UserAuth;
import com.vnc.officeManagementApp.Models.Users;
import com.vnc.officeManagementApp.RequestsDTO.UserSaveDTO;
import com.vnc.officeManagementApp.ResponseDTO.UserSaveResponseDTO;
import com.vnc.officeManagementApp.Services.UserAuthService;
import com.vnc.officeManagementApp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    UserAuthService userAuthService;

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody UserAuth userAuth) throws Exception {
        userAuthService.verifyUsers(userAuth);
        return "Success";
    }

    @PostMapping("/register")
    public ResponseEntity<UserSaveResponseDTO> store(@RequestBody UserSaveDTO userSaveDTO) {
        UserAuth userAuth = new UserAuth();
        userAuth.setUserName(userSaveDTO.getUserName());
        userAuth.setPassword(userSaveDTO.getPassword());
        userAuth.setIsEnabled(true);

        Users users = new Users();
        users.setFirstName(userSaveDTO.getFirstName());
        users.setLastName(userSaveDTO.getLastName());
        users.setContactNumber(userSaveDTO.getContactNumber());

        UserAuth userAuthResult =userAuthService.storeUserAuth(userAuth);
        Users usersResults = userService.storeUsers(users);

        UserSaveResponseDTO userSaveResponseDTO = new UserSaveResponseDTO(userAuthResult, usersResults);

        return new ResponseEntity<>(userSaveResponseDTO, HttpStatus.CREATED);
    }
}

package com.vnc.officeManagementApp.Controllers;

import com.vnc.officeManagementApp.Models.UserAuth;
import com.vnc.officeManagementApp.Models.Users;
import com.vnc.officeManagementApp.RequestsDTO.UserSaveDTO;
import com.vnc.officeManagementApp.ResponseDTO.UserSaveResponseDTO;
import com.vnc.officeManagementApp.Services.JwtService;
import com.vnc.officeManagementApp.Services.UserAuthService;
import com.vnc.officeManagementApp.Services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthController {
    @Autowired
    UserAuthService userAuthService;

    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserAuth userAuth) throws Exception {
        var auth = userAuthService.authenticate(userAuth);
        if(auth != null) {
            var user = User.builder()
                    .username(userAuth.getUsername())
                    .build();

            String token = jwtService.generateToken(userAuth);
            System.out.println("token"+ token);

            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        throw new Exception("Unauthorized");
    }

    @PostMapping("/register")
    public ResponseEntity<String> store(@RequestBody UserSaveDTO userSaveDTO) {
        log.info("Register");
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

//        UserSaveResponseDTO userSaveResponseDTO = new UserSaveResponseDTO(userAuthResult, usersResults);
        var user = User.builder()
                .username(userSaveDTO.getUserName())
                .build();

        String token = jwtService.generateToken(userAuth);
        System.out.println("token"+ token);

        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}

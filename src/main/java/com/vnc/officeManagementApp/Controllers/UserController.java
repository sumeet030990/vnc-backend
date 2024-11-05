package com.vnc.officeManagementApp.Controllers;

import com.vnc.officeManagementApp.Models.Roles;
import com.vnc.officeManagementApp.Models.UserAuth;
import com.vnc.officeManagementApp.Models.Users;
import com.vnc.officeManagementApp.RequestsDTO.UserSaveDTO;
import com.vnc.officeManagementApp.ResponseDTO.SuccessResponseDTO;
import com.vnc.officeManagementApp.ResponseDTO.UserSaveResponseDTO;
import com.vnc.officeManagementApp.Services.RolesService;
import com.vnc.officeManagementApp.Services.UserAuthService;
import com.vnc.officeManagementApp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private RolesService rolesService;

    @GetMapping("")
    public ResponseEntity<List<Users>> index() {
        List<Users> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("")
    public SuccessResponseDTO store(@RequestBody UserSaveDTO userSaveDTO) throws Exception {
        try {
            Optional<Roles> roleOptional = rolesService.findById(userSaveDTO.getRoleId());

            if (roleOptional.isEmpty())
                throw new RuntimeException("Invalid Role");
            Roles role = roleOptional.get(); // Get the actual Role object

            UserAuth userAuthResult = null;
            UserAuth userAuth = null;

            if (userSaveDTO.getUserName() != null) {
                userAuth = userAuthService.createUserAuthObjectFromRequest(userSaveDTO);
                userAuthResult = userAuthService.storeUserAuth(userAuth);
            }

            Users users = userService.createUserObjectFromRequest(userSaveDTO, role, userAuth);
            Users usersResults = userService.storeUsers(users);

            UserSaveResponseDTO userSaveResponseDTO = new UserSaveResponseDTO(userAuthResult, usersResults, null);
            SuccessResponseDTO successResponseDTO = new SuccessResponseDTO(userSaveResponseDTO);

            return successResponseDTO;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}

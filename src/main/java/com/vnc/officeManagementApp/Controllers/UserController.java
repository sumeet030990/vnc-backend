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

    private SuccessResponseDTO saveUserDataUser(UserSaveDTO userSaveDTO) throws Exception {
        try {
            Optional<Roles> roleOptional = rolesService.findById(userSaveDTO.getRoleId());

            if (roleOptional.isEmpty())
                throw new RuntimeException("Invalid Role");
            Roles role = roleOptional.get(); // Get the actual Role object

            UserAuth userAuthResult = null;
            UserAuth userAuthObjectForSaving = null;

            if (userSaveDTO.getUserName() != null) {
                userAuthObjectForSaving = userAuthService.createUserAuthObjectFromRequest(userSaveDTO);
                userAuthResult = userAuthService.storeUserAuth(userAuthObjectForSaving);
            }

            Users users = userService.createUserObjectFromRequest(userSaveDTO, role, userAuthObjectForSaving);
            Users usersResults = userService.storeUsers(users);
            UserSaveResponseDTO userSaveResponseDTO = new UserSaveResponseDTO(userAuthResult, usersResults, null);

            SuccessResponseDTO successResponseDTO = new SuccessResponseDTO(HttpStatus.CREATED, userSaveResponseDTO);

            return successResponseDTO;

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    @PostMapping("")
    public SuccessResponseDTO store(@RequestBody UserSaveDTO userSaveDTO) throws Exception {
        try {
            SuccessResponseDTO result = saveUserDataUser(userSaveDTO);

            return result;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public SuccessResponseDTO update(@RequestBody UserSaveDTO userSaveDTO) throws Exception {
        try {
            Optional<Roles> roleOptional = rolesService.findById(userSaveDTO.getRoleId());

            if (roleOptional.isEmpty())
                throw new RuntimeException("Invalid Role");

            SuccessResponseDTO successResponseDTO = new SuccessResponseDTO("");

            return successResponseDTO;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public SuccessResponseDTO destroy(@PathVariable Long id) throws Exception {
        try {
            userService.deleteuser(id);

            return new SuccessResponseDTO("User successfully Deleted");

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}

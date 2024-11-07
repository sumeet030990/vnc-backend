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
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public SuccessResponseDTO store(@RequestBody UserSaveDTO userSaveDTO) throws Exception {
        try {
            Roles role = validateRole(userSaveDTO.getRoleId());

            UserAuth userAuthResult = null;
            UserAuth userAuthObjectForSaving = null;

            // if user name is present means user login
            if (userSaveDTO.getUserName() != null) {
                userAuthObjectForSaving = userAuthService.createUserAuthObjectFromRequest(userSaveDTO);
                userAuthResult = userAuthService.storeUserAuth(userAuthObjectForSaving);
            }

            Users users = userService.createUserObjectFromRequest(userSaveDTO, role, userAuthObjectForSaving);
            Users usersResults = userService.storeUsers(users);

            // save done, return the response now
            UserSaveResponseDTO userSaveResponseDTO = new UserSaveResponseDTO(userAuthResult, usersResults, null);
            SuccessResponseDTO successResponseDTO = new SuccessResponseDTO(HttpStatus.CREATED, userSaveResponseDTO);

            return successResponseDTO;

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public SuccessResponseDTO update(@RequestBody UserSaveDTO userSaveDTO, @PathVariable("id") Long userId)
            throws Exception {
        try {
            Optional<Users> usersOptional = userService.findUserById(userId);
            if (usersOptional.isEmpty())
                throw new RuntimeException("Invalid User");

            Users userData = usersOptional.get(); // Get the actual User object

            Roles role = validateRole(userSaveDTO.getRoleId());

            UserAuth userAuthResult = null;
            UserAuth userAuthObjectForSaving = null;

            // if user name is present means user login
            if (userSaveDTO.getUserName() != null) {
                // handle userAuth updation
                UserAuth userAuth = userData.getUserAuth();

                userSaveDTO.setUserAuthId(userAuth.getId());
                userAuthObjectForSaving = userAuthService.createUserAuthObjectFromRequest(userSaveDTO);
                userAuthObjectForSaving.setCreatedDateTime(userAuth.getCreatedDateTime());

                userAuthResult = userAuthService.storeUserAuth(userAuthObjectForSaving);
            }

            // handle User updation
            userSaveDTO.setUserId(userId);
            Users users = userService.createUserObjectFromRequest(userSaveDTO, role, userAuthObjectForSaving);
            users.setCreatedDateTime(userData.getCreatedDateTime());
            Users usersResults = userService.storeUsers(users);

            // save done, return the response now
            UserSaveResponseDTO userSaveResponseDTO = new UserSaveResponseDTO(userAuthResult, usersResults, null);
            SuccessResponseDTO successResponseDTO = new SuccessResponseDTO(HttpStatus.OK, userSaveResponseDTO);

            return successResponseDTO;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private Roles validateRole(Long roleId) {
        return rolesService.findById(roleId).orElseThrow(() -> new RuntimeException("Invalid Role"));
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

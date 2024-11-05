package com.vnc.officeManagementApp.Controllers;

import com.vnc.officeManagementApp.Jwt.JwtUtils;
import com.vnc.officeManagementApp.Models.Roles;
import com.vnc.officeManagementApp.Models.UserAuth;
import com.vnc.officeManagementApp.Models.Users;
import com.vnc.officeManagementApp.RequestsDTO.LoginRequestDTO;
import com.vnc.officeManagementApp.RequestsDTO.UserSaveDTO;
import com.vnc.officeManagementApp.ResponseDTO.ErrorResponseDTO;
import com.vnc.officeManagementApp.ResponseDTO.SuccessResponseDTO;
import com.vnc.officeManagementApp.ResponseDTO.UserSaveResponseDTO;
import com.vnc.officeManagementApp.Services.RolesService;
import com.vnc.officeManagementApp.Services.UserAuthService;
import com.vnc.officeManagementApp.Services.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private UserService userService;

    @Autowired
    private RolesService rolesService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) throws Exception {
        Authentication authentication;
        try {
            // Authenticate the user
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    loginRequestDTO.getUserName(), loginRequestDTO.getPassword());
            authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

            // create response object
            Map<String, Object> response = new HashMap<>();
            response.put("jwtToken", jwtToken);
            response.put("userDetails", userDetails);

            SuccessResponseDTO successResponseDTO = new SuccessResponseDTO(response);

            return ResponseEntity.ok(successResponseDTO);
        } catch (Exception exception) {
            ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(exception.getMessage());
            logger.warn("==== AuthController@login Error: " + exception.getMessage());

            return new ResponseEntity<Object>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register")
    @Transactional
    public SuccessResponseDTO store(@RequestBody UserSaveDTO userSaveDTO) throws Exception {
        try {
            Optional<Roles> roleOptional = rolesService.findById(userSaveDTO.getRoleId());

            if (roleOptional.isEmpty())
                throw new RuntimeException("Invalid Role");
            Roles role = roleOptional.get(); // Get the actual Role object

            UserAuth userAuth = userAuthService.createUserAuthObjectFromRequest(userSaveDTO);
            Users users = userService.createUserObjectFromRequest(userSaveDTO, role, userAuth);

            UserAuth userAuthResult = userAuthService.storeUserAuth(userAuth);
            Users usersResults = userService.storeUsers(users);

            String jwtToken = jwtUtils.generateTokenFromUsername(userAuth);
            UserSaveResponseDTO userSaveResponseDTO = new UserSaveResponseDTO(userAuthResult, usersResults, jwtToken);

            SuccessResponseDTO successResponseDTO = new SuccessResponseDTO(userSaveResponseDTO);

            return successResponseDTO;
        } catch (Exception exception) {
            logger.warn("==== AuthController@store Error: " + exception.getMessage());
            throw new Exception(exception.getMessage());
        }
    }
}

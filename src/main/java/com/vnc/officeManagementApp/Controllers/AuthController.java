package com.vnc.officeManagementApp.Controllers;

import com.vnc.officeManagementApp.Config.SecurityConfig;
import com.vnc.officeManagementApp.Jwt.JwtUtils;
import com.vnc.officeManagementApp.Models.Roles;
import com.vnc.officeManagementApp.Models.UserAuth;
import com.vnc.officeManagementApp.Models.Users;
import com.vnc.officeManagementApp.RequestsDTO.UserSaveDTO;
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
import org.springframework.security.core.AuthenticationException;
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
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

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
    public ResponseEntity<?> login(@RequestBody UserAuth userAuth) throws Exception {
        Authentication authentication;
        logger.debug("==== AuthController@login");
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(userAuth.getUsername(), userAuth.getPassword()));
        } catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

//        List<String> roles = userDetails.getAuthorities().stream()
//                .map(item -> item.getAuthority())
//                .collect(Collectors.toList());

//        LoginResponse response = new LoginResponse(userDetails.getUsername(), roles, jwtToken);

        return ResponseEntity.ok(jwtToken);
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<?> store(@RequestBody UserSaveDTO userSaveDTO) throws Exception {
        try {
            logger.debug("==== AuthController@register");

            Optional<Roles> roleOptional = rolesService.findById(userSaveDTO.getRoleId());

            if(roleOptional.isEmpty()) throw new RuntimeException("Invalid Role");
            Roles role = roleOptional.get(); // Get the actual Role object

            UserAuth userAuth = new UserAuth();
            userAuth.setUserName(userSaveDTO.getUserName());
            userAuth.setPassword(userSaveDTO.getPassword());
            userAuth.setIsEnabled(true);

            Users users = new Users();
            users.setFirstName(userSaveDTO.getFirstName());
            users.setLastName(userSaveDTO.getLastName());
            users.setContactNumber(userSaveDTO.getContactNumber());
            users.setRoles(role);
            users.setUserAuth(userAuth);

            UserAuth userAuthResult =userAuthService.storeUserAuth(userAuth);
            Users usersResults = userService.storeUsers(users);

            String jwtToken = jwtUtils.generateTokenFromUsername(userAuth);
            UserSaveResponseDTO userSaveResponseDTO = new UserSaveResponseDTO(userAuthResult, usersResults, jwtToken);
            return new ResponseEntity<>(userSaveResponseDTO, HttpStatus.OK);
        } catch (Exception e) {
           throw new Exception(e.getMessage());
        }
    }
}

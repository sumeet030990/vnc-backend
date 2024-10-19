package com.vnc.officeManagementApp.Services;

import com.vnc.officeManagementApp.Models.AuthUser;
import com.vnc.officeManagementApp.Models.UserAuth;
import com.vnc.officeManagementApp.Repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService implements UserDetailsService {
    @Autowired
    UserAuthRepository userAuthRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder =new BCryptPasswordEncoder(12);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuth userAuth = userAuthRepository.findByUserName(username);
        if(userAuth == null) {
            throw new UsernameNotFoundException("User Not Found");
        }

        return new AuthUser(userAuth);
    }

    public UserAuth storeUserAuth(UserAuth userAuth){
        try {
            userAuth.setPassword(bCryptPasswordEncoder.encode(userAuth.getPassword()));
            return userAuthRepository.save(userAuth);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public String verifyUsers(UserAuth userAuth) throws Exception {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userAuth.getUserName(), userAuth.getPassword()));

        if(authentication.isAuthenticated()) {
            return jwtService.generateToken(userAuth.getUserName());
        }

        throw new Exception("Invalid Credentials");
    }
}

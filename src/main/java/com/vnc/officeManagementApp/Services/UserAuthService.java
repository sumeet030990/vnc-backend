package com.vnc.officeManagementApp.Services;

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
public class UserAuthService{
    @Autowired
    private UserAuthRepository userAuthRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder bCryptPasswordEncoder =new BCryptPasswordEncoder(12);

    public UserAuth storeUserAuth(UserAuth userAuth){
        try {
            userAuth.setPassword(bCryptPasswordEncoder.encode(userAuth.getPassword()));
            return userAuthRepository.save(userAuth);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Authentication authenticate(UserAuth userAuth) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userAuth.getUsername(), userAuth.getPassword())
        );
    }

}

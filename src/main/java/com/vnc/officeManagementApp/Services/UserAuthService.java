package com.vnc.officeManagementApp.Services;

import com.vnc.officeManagementApp.Models.UserAuth;
import com.vnc.officeManagementApp.Repository.UserAuthRepository;
import com.vnc.officeManagementApp.RequestsDTO.UserSaveDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService implements UserDetailsService {
    @Autowired
    private UserAuthRepository userAuthRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public UserAuth storeUserAuth(UserAuth userAuth) {
        try {
            userAuth.setPassword(bCryptPasswordEncoder.encode(userAuth.getPassword()));
            return userAuthRepository.save(userAuth);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuth userAuth = userAuthRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return userAuth;
    }

    /**
     * Create UserAuth Model object for further processing
     * userd in AuthController@register & UserController@store
     * 
     * @param userSaveDTO
     * @return
     * @throws Exception
     */
    public UserAuth createUserAuthObjectFromRequest(UserSaveDTO userSaveDTO) throws Exception {
        try {
            UserAuth userAuth = new UserAuth();
            userAuth.setUserName(userSaveDTO.getUserName());
            userAuth.setPassword(userSaveDTO.getPassword());
            userAuth.setIsEnabled(true);

            return userAuth;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}

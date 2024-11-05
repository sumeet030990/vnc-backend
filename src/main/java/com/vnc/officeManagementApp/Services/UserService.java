package com.vnc.officeManagementApp.Services;

import com.vnc.officeManagementApp.Models.Roles;
import com.vnc.officeManagementApp.Models.UserAuth;
import com.vnc.officeManagementApp.Models.Users;
import com.vnc.officeManagementApp.Repository.UserRepository;
import com.vnc.officeManagementApp.RequestsDTO.UserSaveDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Fetch All list of users
     *
     * @return List<Users>
     */
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Find user by UserName
     * 
     * @param userId
     * @return Optional<Users>
     */
    public Optional<Users> findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * Create UserAuth Model object for further processing
     * userd in AuthController@register & UserController@store
     * 
     * @param userSaveDTO
     * @param role
     * @param userAuth
     * @return
     * @throws Exception
     */
    public Users createUserObjectFromRequest(UserSaveDTO userSaveDTO, Roles role, UserAuth userAuth)
            throws Exception {
        try {
            Users users = new Users();
            users.setFirstName(userSaveDTO.getFirstName());
            users.setLastName(userSaveDTO.getLastName());
            users.setContactNumber(userSaveDTO.getContactNumber());
            users.setRoles(role);

            if (userAuth != null) {
                users.setUserAuth(userAuth);
            }

            return users;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Store Users
     * 
     * @param users
     * @return
     */
    public Users storeUsers(Users users) {
        return userRepository.save(users);
    }

}

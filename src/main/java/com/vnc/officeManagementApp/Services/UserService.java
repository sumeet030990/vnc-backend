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
    public Users findUserById(Long userId) throws Exception {
        try {
            Optional<Users> userOptional = userRepository.findById(userId);
            if (userOptional.isEmpty()) {
                throw new Exception("User Not Found");
            }

            Users users = userOptional.get();
            UserAuth userAuth = users.getUserAuth();

            if (userAuth != null && userAuth.getUsername() != null) {
                users.setUserAuth(userAuth);

            }
            return users;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
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
            users.setAddress(userSaveDTO.getAddress());

            if (userAuth != null) {
                users.setUserAuth(userAuth);
            }

            // handle Updation
            if (userSaveDTO.getUserId() != null) {
                users.setId(userSaveDTO.getUserId());
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

    /**
     * Delete user by user Id
     * 
     * @param userId
     * @return
     * @throws Exception
     */
    public boolean deleteuser(Long userId) throws Exception {
        try {
            Optional<Users> users = userRepository.findById(userId);

            if (!users.isEmpty()) {
                userRepository.deleteById(userId);
                return true;
            }

            throw new Exception("User not found");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}

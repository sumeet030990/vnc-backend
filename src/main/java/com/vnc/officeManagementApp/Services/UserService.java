package com.vnc.officeManagementApp.Services;

import com.vnc.officeManagementApp.Models.Users;
import com.vnc.officeManagementApp.Repository.UserRepository;
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
     * @param userId
     * @return Optional<Users>
     */
    public Optional<Users> findUserById(Long userId) {
        return  userRepository.findById(userId);
    }

    /**
     * Store Users
     * @param users
     * @return
     */
    public Users storeUsers(Users users) {
        return  userRepository.save(users);
    }

}

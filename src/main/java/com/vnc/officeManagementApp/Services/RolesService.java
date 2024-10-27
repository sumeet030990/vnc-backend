package com.vnc.officeManagementApp.Services;

import com.vnc.officeManagementApp.Models.Roles;
import com.vnc.officeManagementApp.Repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolesService {
    @Autowired
    RolesRepository rolesRepository;

    /**
     * Fetch All Roles from the DB
     *
     * @return List<Roles>
     */
    public List<Roles> fetchAllRoles() {
        return rolesRepository.findAll();
    }

    /**
     * Fetch Roles By its Id
     * @param id
     * @return Roles
     */
    public Optional<Roles> findById(Long id) {
        try {
         return rolesRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

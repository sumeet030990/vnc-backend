package com.vnc.officeManagementApp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vnc.officeManagementApp.Models.Items;

public interface ItemRepository extends JpaRepository<Items, Long> {

}

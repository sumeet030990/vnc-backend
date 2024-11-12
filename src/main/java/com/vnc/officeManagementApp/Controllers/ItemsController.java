package com.vnc.officeManagementApp.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vnc.officeManagementApp.Models.Items;
import com.vnc.officeManagementApp.ResponseDTO.SuccessResponseDTO;
import com.vnc.officeManagementApp.Services.ItemService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/items")
public class ItemsController {

    @Autowired
    ItemService itemService;

    @GetMapping("")
    public SuccessResponseDTO index() throws Exception {
        try {
            List<Items> itemsCollection = itemService.fetchAllItems();
            return new SuccessResponseDTO(itemsCollection);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public SuccessResponseDTO show(@PathVariable Long id) throws Exception {
        try {
            Items items = itemService.findById(id);

            return new SuccessResponseDTO(items);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @PostMapping("")
    public SuccessResponseDTO store(@RequestBody Items itemsData) throws Exception {
        try {
            Items items = itemService.store(itemsData);

            return new SuccessResponseDTO(items);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public SuccessResponseDTO update(@RequestBody Items itemsData, @PathVariable Long id) throws Exception {
        try {
            itemsData.setId(id);
            Items items = itemService.store(itemsData);

            return new SuccessResponseDTO(items);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public SuccessResponseDTO destroy(@PathVariable Long id) throws Exception {
        try {
            boolean result = itemService.destroy(id);
            if (result) {
                return new SuccessResponseDTO("Item deleted Successfully");
            }

            return new SuccessResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "Issue in deleting Items");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}

package com.vnc.officeManagementApp.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vnc.officeManagementApp.Models.ItemBrands;
import com.vnc.officeManagementApp.RequestsDTO.ItemBrandsDto;
import com.vnc.officeManagementApp.ResponseDTO.SuccessResponseDTO;
import com.vnc.officeManagementApp.Services.ItemBrandsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/item-brand")
public class ItemBrandsController {

    @Autowired
    ItemBrandsService itemBrandsService;

    @GetMapping("")
    public SuccessResponseDTO index() throws Exception {
        try {
            return new SuccessResponseDTO(itemBrandsService.fetchAllItemBrands());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public SuccessResponseDTO show(@PathVariable Integer id) throws Exception {
        try {
            ItemBrands itemBrands = itemBrandsService.findById(id);

            return new SuccessResponseDTO(itemBrands);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @PostMapping("")
    public SuccessResponseDTO store(@RequestBody ItemBrandsDto itemBrandsDto) throws Exception {
        try {
            ItemBrands itemBrands = itemBrandsService.store(itemBrandsDto);

            return new SuccessResponseDTO(itemBrands);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public SuccessResponseDTO update(@RequestBody ItemBrandsDto itemBrandsData, @PathVariable Integer id)
            throws Exception {
        try {
            itemBrandsData.setId(id);
            ItemBrands itemBrands = itemBrandsService.store(itemBrandsData);

            return new SuccessResponseDTO(itemBrands);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public SuccessResponseDTO destroy(@PathVariable Integer id) throws Exception {
        try {
            boolean result = itemBrandsService.destroy(id);
            if (result) {
                return new SuccessResponseDTO("Item deleted Successfully");
            }

            return new SuccessResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "Issue in deleting Items");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}

package com.vnc.officeManagementApp.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vnc.officeManagementApp.Models.Items;
import com.vnc.officeManagementApp.Repository.ItemRepository;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    /**
     * Fetch All Items
     * 
     * @return
     */
    public List<Items> fetchAllItems() {
        return itemRepository.findAll();
    }

    /**
     * Find Item by id
     * 
     * @param id
     * @return
     * @throws Exception
     */
    public Items findById(Long id) throws Exception {
        Optional<Items> optionalItem = itemRepository.findById(id);
        if (optionalItem.isEmpty())
            throw new Exception("Item Not Found");

        return optionalItem.get();
    }

    /**
     * Save/Update new Items
     * 
     * @param items
     * @return
     * @throws Exception
     */
    public Items store(Items items) throws Exception {
        try {
            return itemRepository.save(items);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public boolean destroy(Long id) throws Exception {
        try {
            Items items = findById(id);
            if (items != null) {
                itemRepository.delete(items);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}

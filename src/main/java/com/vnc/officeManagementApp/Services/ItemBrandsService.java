package com.vnc.officeManagementApp.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vnc.officeManagementApp.Models.ItemBrands;
import com.vnc.officeManagementApp.Models.Items;
import com.vnc.officeManagementApp.Models.Users;
import com.vnc.officeManagementApp.Repository.ItemBrandsRepository;
import com.vnc.officeManagementApp.RequestsDTO.ItemBrandsDto;

@Service
public class ItemBrandsService {
    @Autowired
    ItemBrandsRepository itemBrandsRepository;

    @Autowired
    ItemService itemService;

    @Autowired
    UserService userService;

    public List<ItemBrands> fetchAllItemBrands() {
        return itemBrandsRepository.findAll();
    }

    public Page<ItemBrands> fetchAllItemBrandsWithPagination(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ItemBrands> itemBrandPage = itemBrandsRepository.findAll(pageable);

        return itemBrandPage;
    }

    /**
     * Item Brand fetch By its id
     * 
     * @param id
     * @return
     * @throws Exception
     */
    public ItemBrands findById(Integer id) throws Exception {
        try {
            Optional<ItemBrands> itemBrandOptional = itemBrandsRepository.findById(id);
            if (itemBrandOptional.isEmpty()) {
                throw new Exception("Item Brand not found");
            }

            return itemBrandOptional.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private ItemBrands createItemBrandsObject(ItemBrandsDto itemBrandsDto) throws Exception {
        try {
            Items items = itemService.findById(itemBrandsDto.getItemsId());
            Users users = userService.findUserById(itemBrandsDto.getUsersId());

            ItemBrands itemBrands = new ItemBrands();
            itemBrands.setName(itemBrandsDto.getName());
            itemBrands.setSlug(itemBrandsDto.getSlug());
            itemBrands.setItems(items);
            itemBrands.setUsers(users);

            if (itemBrandsDto.getId() != null) {
                itemBrands.setId(itemBrandsDto.getId());
            }

            return itemBrands;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Save/Update ItemBrands
     * 
     * @param itemBrands
     * @return
     * @throws Exception
     */
    public ItemBrands store(ItemBrandsDto itemBrandsDto) throws Exception {
        try {
            ItemBrands itemBrands = createItemBrandsObject(itemBrandsDto);

            return itemBrandsRepository.save(itemBrands);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Delete ItemBrands
     * 
     * @param id
     * @return
     * @throws Exception
     */
    public boolean destroy(Integer id) throws Exception {
        try {
            ItemBrands itemBrands = findById(id);
            if (itemBrands != null) {
                itemBrandsRepository.delete(itemBrands);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}

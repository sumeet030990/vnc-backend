package com.vnc.officeManagementApp.RequestsDTO;

import lombok.Data;

@Data
public class ItemBrandsDto {
    private Integer id;
    private String slug;
    private String name;
    private Integer itemsId;
    private Long usersId;
}

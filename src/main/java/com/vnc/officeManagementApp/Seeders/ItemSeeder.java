package com.vnc.officeManagementApp.Seeders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.vnc.officeManagementApp.Models.Items;
import com.vnc.officeManagementApp.Repository.ItemRepository;

@Configuration
public class ItemSeeder {

    @Bean
    CommandLineRunner initItemSeeder(ItemRepository itemRepository) {
        return args -> {
            itemRepository.save(new Items(1, "toor_dal", "Toor Dal"));
            itemRepository.save(new Items(2, "chana_dal", "Chana Dal"));
        };
    }
}

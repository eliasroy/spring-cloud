package com.daemon.inventoryservice.utils;

import com.daemon.inventoryservice.model.entities.Inventory;
import com.daemon.inventoryservice.repositorie.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader  implements CommandLineRunner {

    private final InventoryRepository inventoryRepository;
    @Override
    public void run(String... args) throws Exception {
        if (inventoryRepository.findAll().size()==0){
            inventoryRepository.saveAll(List.of(
                    Inventory.builder().build()
            ));
        }
    }
}

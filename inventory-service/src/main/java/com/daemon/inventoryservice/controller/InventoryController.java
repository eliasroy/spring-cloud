package com.daemon.inventoryservice.controller;

import com.daemon.inventoryservice.model.dtos.BaseResponse;
import com.daemon.inventoryservice.model.dtos.OrderItemsRequest;


import com.daemon.inventoryservice.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("/{sku}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable String sku){
        return inventoryService.isInStock(sku);
    }
    @PostMapping("/in-stock")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse isInStock(@RequestBody List<OrderItemsRequest> orderItemsRequests){
        return inventoryService.areInStock(orderItemsRequests);
    }
}

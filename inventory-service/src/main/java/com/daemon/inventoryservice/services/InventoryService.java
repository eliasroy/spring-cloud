package com.daemon.inventoryservice.services;

import com.daemon.inventoryservice.model.dtos.BaseResponse;
import com.daemon.inventoryservice.model.dtos.OrderItemsRequest;
import com.daemon.inventoryservice.model.entities.Inventory;
import com.daemon.inventoryservice.repositorie.InventoryRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public boolean isInStock(String sku) {
        var inventory = inventoryRepository.findBySku(sku);
        return inventory.filter(vale->vale.getQuantity()>0).isPresent();
    }

    public BaseResponse areInStock(List<OrderItemsRequest> orderItemsRequests) {
        var  errorList=new ArrayList<>();
        List<String>skus=orderItemsRequests.stream().map(OrderItemsRequest::getSku).toList();
        List<Inventory> inventoryList=inventoryRepository.findBySkuIn(skus);

        orderItemsRequests.forEach(orderItemsRequest -> {
            var inventory=inventoryList.stream().filter(v->v.getSku().equals(orderItemsRequest.getSku())).findFirst();
            if(inventory.isEmpty()){
                errorList.add("Inventory not found for sku "+orderItemsRequest.getSku());
            }else if (inventory.get().getQuantity()<orderItemsRequest.getQuantity()){
                errorList.add("Inventory insuficiente cantidad ::    "+orderItemsRequest.getSku());
            }
        });
        return errorList.size()>0?new BaseResponse(errorList.toArray(new  String[0])):new BaseResponse(null);


    }
}


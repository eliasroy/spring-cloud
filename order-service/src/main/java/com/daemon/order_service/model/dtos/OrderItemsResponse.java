package com.daemon.order_service.model.dtos;

public record OrderItemsResponse(Long id, String skuCode, Double price,Long quantity) {
}

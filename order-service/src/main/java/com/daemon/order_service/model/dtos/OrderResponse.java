package com.daemon.order_service.model.dtos;

import lombok.extern.java.Log;

import java.util.List;

public record OrderResponse (Long id, String orderNumber, List<OrderItemsResponse> orderItemsList){
}

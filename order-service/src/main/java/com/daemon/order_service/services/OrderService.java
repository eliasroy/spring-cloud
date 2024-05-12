package com.daemon.order_service.services;

import com.daemon.order_service.model.dtos.OrderItemsRequest;
import com.daemon.order_service.model.dtos.OrderRequest;
import com.daemon.order_service.model.entities.Order;
import com.daemon.order_service.model.entities.OrderItems;
import com.daemon.order_service.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private  final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest){
        //check for inventory if has stock
        Order order=new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderItems(orderRequest.getOrderItems().stream()
                .map(orderItemRequest -> mapToOrderItemRequestToOrderItem(orderItemRequest,order)).toList());
        this.orderRepository.save(order);
    }

    private OrderItems mapToOrderItemRequestToOrderItem(OrderItemsRequest orderItemRequest, Order order) {
        return OrderItems.builder()
                .id(orderItemRequest.getId())
                .sku(orderItemRequest.getSku())
                .price(orderItemRequest.getPrice())
                .quantity(orderItemRequest.getQuantity())
                .order(order)
                .build();
    }
}

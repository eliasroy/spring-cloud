package com.daemon.order_service.services;

import com.daemon.order_service.model.dtos.BaseResponse;
import com.daemon.order_service.model.dtos.OrderItemsRequest;
import com.daemon.order_service.model.dtos.OrderRequest;
import com.daemon.order_service.model.entities.Order;
import com.daemon.order_service.model.entities.OrderItems;
import com.daemon.order_service.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private  final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public void placeOrder(OrderRequest orderRequest){
        //check for inventory if has stock

        BaseResponse result= this.webClientBuilder.build()
                .post()
                .uri("http://localhost:8083/api/inventory/in-stock")
                .bodyValue(orderRequest.getOrderItems())
                .retrieve()
                .bodyToMono(BaseResponse.class)
                .block();

        if (result!=null && !result.hasErrors()){
            Order order=new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setOrderItems(orderRequest.getOrderItems().stream()
                    .map(orderItemRequest -> mapToOrderItemRequestToOrderItem(orderItemRequest,order)).toList());
            this.orderRepository.save(order);
        }else {
            throw new IllegalArgumentException("Some of the products are not in stock.");
        }
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

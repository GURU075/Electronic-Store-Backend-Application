package com.guru.electronic.strore.services;

import com.guru.electronic.strore.dtos.CreateOrderRequest;
import com.guru.electronic.strore.dtos.OrderDto;
import com.guru.electronic.strore.dtos.PageableResponse;

import java.util.List;

public interface OrderService {

    //create order
    OrderDto createOrder(CreateOrderRequest orderDto);

    //remove order
    void removeOrder(String orderId);

    //get order
    PageableResponse<OrderDto> getOrder(int pageNumber,int pageSize,String sortBy,String sortDirection);

    //get order of user
    List<OrderDto> getOrdersOfUser(int userId);


}

package com.guru.electronic.strore.controllers;

import com.guru.electronic.strore.dtos.CreateOrderRequest;
import com.guru.electronic.strore.dtos.OrderDto;
import com.guru.electronic.strore.dtos.PageableResponse;
import com.guru.electronic.strore.repositories.OrderRepository;
import com.guru.electronic.strore.services.OrderService;
import jakarta.persistence.AttributeOverride;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody CreateOrderRequest request){
        OrderDto orderDto =  orderService.createOrder(request);
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<PageableResponse<OrderDto>> getAllOrders(
            @RequestParam(value = "pageNumber" ,defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10",required = false)int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "billingName",required = false)String sortBy,
            @RequestParam(value = "sortDirection" , defaultValue = "asc",required = false)String sortDirection
    ){
       PageableResponse<OrderDto> response = orderService.getOrder(pageNumber,pageSize,sortBy,sortDirection);
       return new ResponseEntity<>(response,HttpStatus.OK);

    }
}

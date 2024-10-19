package com.guru.electronic.strore.dtos;

import com.guru.electronic.strore.entities.Order;
import com.guru.electronic.strore.entities.Product;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDto {

    private int orderItemId;

    private int quantity;

    private int totalPrice;

    private ProductDto product;

    private OrderDto order;
}

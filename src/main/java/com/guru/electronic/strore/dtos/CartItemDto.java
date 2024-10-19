package com.guru.electronic.strore.dtos;

import com.guru.electronic.strore.entities.Cart;
import com.guru.electronic.strore.entities.Product;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CartItemDto {


    private int cartItemId;
    private ProductDto product;
    private int quantity;
    private int totalPrice;

}

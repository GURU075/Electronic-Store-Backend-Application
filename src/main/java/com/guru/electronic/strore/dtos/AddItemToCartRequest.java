package com.guru.electronic.strore.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AddItemToCartRequest {

    private String productId;
    private int quantity;
}

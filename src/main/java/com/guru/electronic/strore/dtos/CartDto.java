package com.guru.electronic.strore.dtos;

import com.guru.electronic.strore.entities.CartItem;
import com.guru.electronic.strore.entities.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CartDto {

    private String cartId;

    private LocalDate createdAt;

    private UserDto user;

    private List<CartItemDto> cartItems=new ArrayList<>();
}

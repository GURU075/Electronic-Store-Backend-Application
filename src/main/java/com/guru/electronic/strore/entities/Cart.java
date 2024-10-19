package com.guru.electronic.strore.entities;

import jakarta.persistence.*;
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
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    private String cartId;

    private LocalDate createdAt;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "cart",orphanRemoval = true, cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    private List<CartItem> cartItems=new ArrayList<>();
}

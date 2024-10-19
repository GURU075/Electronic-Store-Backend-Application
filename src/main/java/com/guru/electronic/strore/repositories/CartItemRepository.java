package com.guru.electronic.strore.repositories;

import com.guru.electronic.strore.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Integer> {

}

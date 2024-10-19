package com.guru.electronic.strore.repositories;

import com.guru.electronic.strore.entities.Cart;
import com.guru.electronic.strore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,String> {

    Optional<Cart> findByUser(User user);
}

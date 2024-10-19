package com.guru.electronic.strore.repositories;

import com.guru.electronic.strore.entities.Order;
import com.guru.electronic.strore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order ,String> {

    List<Order> findByUser(User user);
}

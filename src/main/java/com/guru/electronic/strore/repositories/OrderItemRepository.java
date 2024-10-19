package com.guru.electronic.strore.repositories;

import com.guru.electronic.strore.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem ,Integer> {

}

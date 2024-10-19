package com.guru.electronic.strore.repositories;

import com.guru.electronic.strore.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,String> {
}

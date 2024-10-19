package com.guru.electronic.strore.repositories;

import com.guru.electronic.strore.entities.Category;
import com.guru.electronic.strore.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String> {

    Page<Product> findByTitleContaining(Pageable pageable,String subTitle);
    Page<Product> findByLiveTrue(Pageable pageable);
    Page<Product> findByLiveTrueAndStockTrue(Pageable pageable);

    Page<Product> findByCategory(Category category,Pageable pageable);

}

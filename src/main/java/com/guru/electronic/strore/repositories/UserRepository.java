package com.guru.electronic.strore.repositories;

import com.guru.electronic.strore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);
    User findByEmailAndPassword(String email, String password);

    List<User> findByNameContaining(String keyword);
}

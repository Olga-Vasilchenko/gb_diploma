package com.example.Shop.repositories;

import com.example.Shop.dto.Registration;
import com.example.Shop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email); // метод для security (как он будет искать пользователя по User_name)


    }



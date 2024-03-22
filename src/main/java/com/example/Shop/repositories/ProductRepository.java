package com.example.Shop.repositories;

import com.example.Shop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // найдет товары по его title (заголовку) и вернет список всех товаров
    List<Product> findByTitle(String title);
}

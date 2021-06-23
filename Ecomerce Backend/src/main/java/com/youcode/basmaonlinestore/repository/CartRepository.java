package com.youcode.basmaonlinestore.repository;

import com.youcode.basmaonlinestore.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartRepository extends JpaRepository<Cart, Integer> {
}

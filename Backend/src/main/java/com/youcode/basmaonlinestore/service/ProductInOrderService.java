package com.youcode.basmaonlinestore.service;

import com.youcode.basmaonlinestore.entity.ProductInOrder;
import com.youcode.basmaonlinestore.entity.User;


public interface ProductInOrderService {
    void update(String itemId, Integer quantity, User user);
    ProductInOrder findOne(String itemId, User user);
}

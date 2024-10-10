package com.example.demo.repository;

import com.example.demo.model.Order;

public interface OrderRepository {
    Order save(Order order);
}

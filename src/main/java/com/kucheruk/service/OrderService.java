package com.kucheruk.service;

import com.kucheruk.domain.Order;
import com.kucheruk.domain.User;
import com.kucheruk.service.Impl.OrderServiceImpl;

import java.util.List;


public interface OrderService {

    List<Order> findAll();

    Order save(Order order);

    List<Order> findOrderByUser(User user);
}

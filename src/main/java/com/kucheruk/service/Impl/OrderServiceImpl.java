package com.kucheruk.service.Impl;

import com.kucheruk.domain.Order;
import com.kucheruk.domain.User;
import com.kucheruk.repos.OrderRepository;
import com.kucheruk.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }


    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }


    @Override
    public List<Order> findOrderByUser(User user) {
        return orderRepository.findOrderByUser(user);
    }
}

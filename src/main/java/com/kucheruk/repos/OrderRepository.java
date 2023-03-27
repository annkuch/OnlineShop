package com.kucheruk.repos;

import com.kucheruk.domain.Order;
import com.kucheruk.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrderByUser(User user);
}
package com.kucheruk.controller;

import com.kucheruk.domain.Order;
import com.kucheruk.domain.User;
import com.kucheruk.service.OrderService;
import com.kucheruk.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


@Controller
@Slf4j
public class OrderController {
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public OrderController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/order")
    public String getOrder(@AuthenticationPrincipal User userSession, Model model) {
        User userFromDB = userService.findByUsername(userSession.getUsername());
        model.addAttribute("perfumes", userFromDB.getPerfumeList());

        return "order/order";
    }


    @PostMapping("/order")
    public String postOrder(
            @AuthenticationPrincipal User userSession,
            @Valid Order validOrder,
            BindingResult bindingResult,
            Model model
    ) {
        User user = userService.findByUsername(userSession.getUsername());
        Order order = new Order(user);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute("perfumes", user.getPerfumeList());

            return "order/order";
        } else {
            order.getPerfumeList().addAll(user.getPerfumeList());
            order.setTotalPrice(validOrder.getTotalPrice());
            order.setFirstName(validOrder.getFirstName());
            order.setLastName(validOrder.getLastName());
            order.setCity(validOrder.getCity());
            order.setAddress(validOrder.getAddress());
            order.setPostIndex(validOrder.getPostIndex());
            order.setEmail(validOrder.getEmail());
            order.setPhoneNumber(validOrder.getPhoneNumber());

            user.getPerfumeList().clear();

            orderService.save(order);

            log.debug("User {} id={} made an order: FirstName={}, LastName={}, TotalPrice={}, City={}, " +
                            "Address={}, PostIndex={}, Email={}, PhoneNumber={}",
                    user.getUsername(), user.getId(), order.getFirstName(), order.getLastName(), order.getTotalPrice(),
                    order.getCity(), order.getAddress(), order.getPostIndex(), order.getEmail(), order.getPhoneNumber());
        }

        return "redirect:/finalizeOrder";
    }


    @GetMapping("/finalizeOrder")
    public String finalizeOrder(Model model) {
        List<Order> orderList = orderService.findAll();
        Order orderIndex = orderList.get(orderList.size() - 1);

        model.addAttribute("orderIndex", orderIndex.getId());

        return "order/finalizeOrder";
    }

    @GetMapping("/userOrders")
    public String getUserOrdersList(@AuthenticationPrincipal User userSession, Model model) {
        User userFromDB = userService.findByUsername(userSession.getUsername());
        List<Order> orders = orderService.findOrderByUser(userFromDB);
        model.addAttribute("orders", orders);

        return "order/orders";
    }

    @GetMapping("/orders")
    public String getAllOrdersList(Model model) {
        List<Order> orders = orderService.findAll();
        model.addAttribute("orders", orders);

        return "order/orders";
    }
}

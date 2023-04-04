package com.kucheruk.controller;

import com.kucheruk.domain.Perfume;
import com.kucheruk.domain.User;
import com.kucheruk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {
      private final UserService userService;


    @Autowired
    public CartController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/cart")
    public String getCart(@AuthenticationPrincipal User userSession, Model model) {
        User userFromDB = userService.findByUsername(userSession.getUsername());
        model.addAttribute("perfumes", userFromDB.getPerfumeList());

        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(
            @RequestParam("add") Perfume perfume,
            @AuthenticationPrincipal User userSession
    ) {
        User user = userService.findByUsername(userSession.getUsername());
        user.getPerfumeList().add(perfume);
        userService.save(user);

        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(
            @RequestParam(value = "perfumeId") Perfume perfume,
            @AuthenticationPrincipal User userSession
    ) {
        User user = userService.findByUsername(userSession.getUsername());

        if (perfume != null) {
            user.getPerfumeList().remove(perfume);
        }

        userService.save(user);

        return "redirect:/cart";
    }
}
package com.kucheruk.controller;

import com.kucheruk.domain.Perfume;
import com.kucheruk.service.PerfumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {

    private final PerfumeService perfumeService;

    @Autowired
    public MainController(PerfumeService perfumeService) {
        this.perfumeService = perfumeService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Perfume> perfumes = perfumeService.findAll();
        model.addAttribute("perfumes", perfumes);

        return "main";
    }


    @GetMapping("/contacts")
    public String getContacts() {
        return "contacts";
    }


    @GetMapping("/cabinet")
    public String userCabinet() {
        return "user/userCabinet";
    }


    @GetMapping("/search")
    public String search(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC, size = 12) Pageable pageable,
            @RequestParam String filter,
            Model model
    ) {
        Page<Perfume> page = perfumeService.findByPerfumerOrPerfumeTitle(filter, filter, pageable);
        int[] pagination = ControllerUtils.computePagination(page);

        model.addAttribute("pagination", pagination);
        model.addAttribute("url", "/menu");
        model.addAttribute("page", page);

        return "menu";
    }


    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable("id") Perfume perfume, Model model) {
        model.addAttribute("perfume", perfume);

        return "product";
    }
}
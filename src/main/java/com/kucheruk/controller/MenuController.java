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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/menu")
public class MenuController {

    private final PerfumeService perfumeService;

    @Autowired
    public MenuController(PerfumeService perfumeService) {
        this.perfumeService = perfumeService;
    }

    @GetMapping
    public String mainMenu(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC, size = 12) Pageable pageable, Model model) {
        Page<Perfume> page = perfumeService.findAll(pageable);
        int[] pagination = ControllerUtils.computePagination(page);
        getMinMaxPerfumePrice(model);

        model.addAttribute("pagination", pagination);
        model.addAttribute("url", "/menu");
        model.addAttribute("page", page);

        return "menu";
    }

    @GetMapping("{perfumer}")
    public String findByPerfumer(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC, size = 12) Pageable pageable,
            @PathVariable String perfumer,
            Model model
    ) {
        Page<Perfume> perfumeList = perfumeService.findByPerfumer(perfumer, pageable);
        int[] pagination = ControllerUtils.computePagination(perfumeList);
        getMinMaxPerfumePrice(model);

        model.addAttribute("pagination", pagination);
        model.addAttribute("url", "/menu/" + perfumer);
        model.addAttribute("page", perfumeList);

        return "menu";
    }

    @GetMapping("gender/{gender}")
    public String findByPerfumeGender(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC, size = 12) Pageable pageable,
            @PathVariable("gender") String perfumeGender,
            Model model
    ) {
        Page<Perfume> gender = perfumeService.findByPerfumeGender(perfumeGender, pageable);
        int[] pagination = ControllerUtils.computePagination(gender);
        getMinMaxPerfumePrice(model);

        model.addAttribute("pagination", pagination);
        model.addAttribute("url", "/menu/gender/" + perfumeGender);
        model.addAttribute("page", gender);

        return "menu";
    }


    @GetMapping("search")
    public String searchByParameters(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC, size = 12) Pageable pageable,
            @RequestParam(value = "gender", required = false, defaultValue = "") List<String> gender,
            @RequestParam(value = "perfumers", required = false, defaultValue = "") List<String> perfumers,
            @RequestParam(value = "startingPrice", required = false, defaultValue = "0") Integer startingPrice,
            @RequestParam(value = "endingPrice", required = false, defaultValue = "0") Integer endingPrice,
            Model model
    ) {
        StringBuilder urlBuilder = new StringBuilder();
        Page<Perfume> perfumesSearch = null;
        getMinMaxPerfumePrice(model);

        if (gender.isEmpty() && perfumers.isEmpty()) {
            Page<Perfume> priceRange = perfumeService.findByPriceBetween(startingPrice, endingPrice, pageable);
            int[] pagination = ControllerUtils.computePagination(priceRange);

            model.addAttribute("pagination", pagination);
            model.addAttribute("url", "/menu/search?startingPrice=" + startingPrice + "&endingPrice=" + endingPrice);
            model.addAttribute("page", priceRange);

            return "menu";
        }

        if (gender.isEmpty()) {
            perfumesSearch = perfumeService.findByPerfumerIn(perfumers, pageable);
            urlBuilder = ControllerUtils.getUrlBuilder(perfumers);
        } else if (perfumers.isEmpty()) {
            perfumesSearch = perfumeService.findByPerfumeGenderIn(gender, pageable);
            urlBuilder = ControllerUtils.getUrlBuilder(gender);
        } else if (!gender.isEmpty() && !perfumers.isEmpty()) {
            perfumesSearch = perfumeService.findByPerfumerInAndPerfumeGenderIn(perfumers, gender, pageable);
            List<String> urlArray = new ArrayList<String>(perfumers);
            urlArray.addAll(gender);
            urlBuilder = ControllerUtils.getUrlBuilder(urlArray);
        }

        int[] pagination = ControllerUtils.computePagination(perfumesSearch);

        model.addAttribute("pagination", pagination);
        model.addAttribute("url", "/menu/search" + urlBuilder);
        model.addAttribute("page", perfumesSearch);

        return "menu";
    }

    private void getMinMaxPerfumePrice(Model model) {
        BigDecimal minPerfumePrice = perfumeService.minPerfumePrice();
        BigDecimal maxPerfumePrice = perfumeService.maxPerfumePrice();

        model.addAttribute("minPerfumePrice", minPerfumePrice);
        model.addAttribute("maxPerfumePrice", maxPerfumePrice);
    }
}
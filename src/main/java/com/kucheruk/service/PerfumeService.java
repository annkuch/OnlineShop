package com.kucheruk.service;

import com.kucheruk.domain.Perfume;
import com.kucheruk.service.Impl.PerfumeServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface PerfumeService {

    List<Perfume> findAll();

    Page<Perfume> findAll(Pageable pageable);

    Page<Perfume> findByPriceBetween(Integer startingPrice, Integer endingPrice, Pageable pageable);


    Page<Perfume> findByPerfumer(String perfumer, Pageable pageable);


    Page<Perfume> findByPerfumeGender(String perfumeGender, Pageable pageable);


    Page<Perfume> findByPerfumeGenderIn(List<String> perfumeGenders, Pageable pageable);


    Page<Perfume> findByPerfumerOrPerfumeTitle(String perfumer, String perfumeTitle, Pageable pageable);


    Page<Perfume> findByPerfumerInAndPerfumeGenderIn(List<String> perfumers, List<String> genders, Pageable pageable);

    Page<Perfume> findByPerfumerInOrPerfumeGenderIn (List<String> perfumers, List<String> genders, Pageable pageable);


    Page<Perfume> findByPerfumerIn (List<String> perfumers, Pageable pageable);

    BigDecimal minPerfumePrice();


    BigDecimal maxPerfumePrice();


    void saveProductInfoById(String perfumeTitle, String perfumer, Integer year, String country, String perfumeGender,
                             String fragranceTopNotes, String fragranceMiddleNotes, String fragranceBaseNotes, String description,
                             String filename, Integer price, String volume, String type, Long id);


    Perfume save(Perfume perfume);
}
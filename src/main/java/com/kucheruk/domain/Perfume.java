package com.kucheruk.domain;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "perfumer", "perfumeTitle", "perfumeGender", "price"})
public class Perfume {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;


    @NotBlank(message = "Будь ласка, заповніть поле")
    @Length(max = 255)
    private String perfumeTitle;


    @NotBlank(message = "Будь ласка, заповніть поле")
    @Length(max = 255)
    private String perfumer;

    @NotNull(message = "Будь ласка, заповніть поле")
    private Integer year;


    @NotBlank(message = "Будь ласка, заповніть поле")
    @Length(max = 255)
    private String country;


    @NotBlank(message = "Будь ласка, заповніть поле")
    @Length(max = 255)
    private String perfumeGender;

    @NotBlank(message = "Будь ласка, заповніть поле")
    @Length(max = 255)
    private String fragranceTopNotes;


    @NotBlank(message = "Будь ласка, заповніть поле")
    @Length(max = 255)
    private String fragranceMiddleNotes;


    @NotBlank(message = "Будь ласка, заповніть поле")
    @Length(max = 255)
    private String fragranceBaseNotes;

    private String description;

    private String filename;

    @NotNull(message = "Будь ласка, заповніть поле")
    private Integer price;


    @NotBlank(message = "Будь ласка, заповніть поле")
    @Length(max = 255)
    private String volume;


    @NotBlank(message = "Будь ласка, заповніть поле")
    @Length(max = 255)
    private String type;
}
package com.kucheruk.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "user", "perfumeList"})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double totalPrice;

    private LocalDate date;

    @NotBlank(message = "Будь ласка, заповніть поле")
    private String firstName;

    @NotBlank(message = "Будь ласка, заповніть поле")
    private String lastName;

    @NotBlank(message = "Будь ласка, заповніть поле")
    private String city;

    @NotBlank(message = "Будь ласка, заповніть поле")
    private String address;

    @Email(message = "Некоректний email")
    @NotBlank(message = "Email не може бути пустим")
    private String email;

    @NotBlank(message = "Номер телефона не може бути пустим")
    private String phoneNumber;

    @NotNull(message = "Почтовий індекс не може бути пустим")
    @Min(value = 5, message = "Поштовий індекс має містити 5 цифр")
    private Integer postIndex;
    @ManyToOne
    private User user;

    @OrderColumn
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Perfume> perfumeList;



    public Order(User user) {
        this.date = LocalDate.now();
        this.user = user;
        this.perfumeList = new ArrayList<>();
    }
}

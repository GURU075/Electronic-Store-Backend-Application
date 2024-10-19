package com.guru.electronic.strore.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="products")
public class Product {

    @Id
    private String productId;

    @Column(length = 10000)
    private String title;

    private int price;

    private int discountedPrice;

    private int quantity;

    private LocalDate addedDate;

    private boolean live;

    private boolean stock;

    private String productImageName;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoryId")
    private Category category;
}

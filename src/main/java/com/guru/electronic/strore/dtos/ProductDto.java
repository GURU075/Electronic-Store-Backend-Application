package com.guru.electronic.strore.dtos;

import com.guru.electronic.strore.entities.Category;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductDto {


    private String productId;


    private String title;

    private int price;

    private int discountedPrice;

    private int quantity;

    private LocalDate addedDate;

    private boolean live;

    private boolean stock;

    private String productImageName;

    private CategoryDto category;
}

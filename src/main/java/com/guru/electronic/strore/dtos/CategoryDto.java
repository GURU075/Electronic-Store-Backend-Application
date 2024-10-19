package com.guru.electronic.strore.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private String categoryId;


    @Size(min = 4,message = "title must be of minimum 4 characters!!")
    @NotBlank(message = "title is must" )
    private String title;

    @NotBlank(message = "description must not be blank!!")
    private String description;

    private String coverImage;
}

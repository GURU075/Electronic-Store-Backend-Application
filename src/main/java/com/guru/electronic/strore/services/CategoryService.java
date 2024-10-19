package com.guru.electronic.strore.services;

import com.guru.electronic.strore.dtos.CategoryDto;
import com.guru.electronic.strore.dtos.PageableResponse;

public interface CategoryService {
    //create
    CategoryDto create(CategoryDto categoryDto);

    //update
    CategoryDto update(CategoryDto categoryDto,String categoryId);

    //delete
    void delete(String categoryId);

    //get all
    PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize,String sortBy,String sortDirection);

    //get single category details
    CategoryDto get(String categoryId);

    //search
}

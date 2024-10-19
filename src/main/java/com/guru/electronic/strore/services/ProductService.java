package com.guru.electronic.strore.services;

import com.guru.electronic.strore.dtos.PageableResponse;
import com.guru.electronic.strore.dtos.ProductDto;
import com.guru.electronic.strore.entities.Product;

import java.util.List;

public interface ProductService {

    //create
     ProductDto create(ProductDto productDto);

    //update
    ProductDto update(ProductDto product,String productId);

    //delete
    void delete(String productId);

    //get Single
    ProductDto getById(String productId);

    //getAll
    PageableResponse<ProductDto> getAll(int pageNumber, int pageSize,String sortBy,String sortDirection);

    //get ALL live
    PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize,String sortBy,String sortDirection);

    //search
    PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber, int pageSize, String sortBy, String sortDirection);

    //create product with category

    ProductDto createWithCategory(ProductDto productDto,String categoryId);

    ProductDto updateCategory(String productId,String categoryId);

    PageableResponse<ProductDto> getAllOfCategory(String categoryId,int pageNumber, int pageSize,String sortBy,String sortDirection);
}

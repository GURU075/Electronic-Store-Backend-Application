package com.guru.electronic.strore.serviceImpls;

import com.guru.electronic.strore.dtos.CategoryDto;
import com.guru.electronic.strore.dtos.PageableResponse;
import com.guru.electronic.strore.entities.Category;
import com.guru.electronic.strore.exceptions.ResourceNotFoundException;
import com.guru.electronic.strore.helper.Helper;
import com.guru.electronic.strore.repositories.CategoryRepository;
import com.guru.electronic.strore.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        String categoryId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);
        Category category = mapper.map(categoryDto,Category.class);
        Category savedCategory = categoryRepository.save(category);
        return mapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {
        Category oldCategory = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category with this id not exist"));
        oldCategory.setDescription(categoryDto.getDescription());
        oldCategory.setTitle(categoryDto.getTitle());
        oldCategory.setCoverImage(categoryDto.getCoverImage());
        Category updatedCategory = categoryRepository.save(oldCategory);
        return mapper.map(updatedCategory,CategoryDto.class);
    }

    @Override
    public void delete(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category not found"));
             categoryRepository.delete(category);
    }

    @Override
    public PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize,String sortBy,String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("desc"))?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable pageable =  PageRequest.of(pageNumber,pageSize,sort);
        Page<Category> page = categoryRepository.findAll(pageable);
        PageableResponse<CategoryDto> response = Helper.getPageableResponse(page,CategoryDto.class);
        return response;
    }

    @Override
    public CategoryDto get(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category with this id not found"));

        return mapper.map(category,CategoryDto.class);
    }
}

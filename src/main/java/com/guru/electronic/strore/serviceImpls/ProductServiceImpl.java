package com.guru.electronic.strore.serviceImpls;

import com.guru.electronic.strore.dtos.CategoryDto;
import com.guru.electronic.strore.dtos.PageableResponse;
import com.guru.electronic.strore.dtos.ProductDto;
import com.guru.electronic.strore.entities.Category;
import com.guru.electronic.strore.entities.Product;
import com.guru.electronic.strore.exceptions.ResourceNotFoundException;
import com.guru.electronic.strore.helper.Helper;
import com.guru.electronic.strore.repositories.CategoryRepository;
import com.guru.electronic.strore.repositories.ProductRepository;
import com.guru.electronic.strore.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ProductDto create(ProductDto productDto) {
        String id = UUID.randomUUID().toString();
        productDto.setProductId(id);
        LocalDate date = LocalDate.now();
        productDto.setAddedDate(date);
        if(productDto.getQuantity()>0){
            productDto.setStock(true);
        }
        Product product = mapper.map(productDto,Product.class);
        product = productRepository.save(product);
        return mapper.map(product,ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto product, String productId) {
        Product product1 = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product not exist"));
        product1.setAddedDate(product.getAddedDate());
        product1.setLive(product.isLive());
        product1.setTitle(product.getTitle());
        product1.setDiscountedPrice(product.getDiscountedPrice());
        product1.setPrice(product.getPrice());
        product1.setStock(product.isStock());
        product1.setProductImageName(product.getProductImageName());
        Product updatedProduct =productRepository.save(product1);

        return mapper.map(updatedProduct,ProductDto.class);
    }

    @Override
    public void delete(String  productId) {
        Product product1 = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product not exist"));
         productRepository.delete(product1);
    }

    @Override
    public ProductDto getById(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product not exist"));
        return mapper.map(product,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("ASC"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findAll(pageable);
        PageableResponse<ProductDto> response = Helper.getPageableResponse(page,ProductDto.class);
        return response;
    }

    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("ASC"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByLiveTrue(pageable);
        PageableResponse<ProductDto> response = Helper.getPageableResponse(page,ProductDto.class);
        return response;
    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("ASC"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByTitleContaining(pageable,subTitle);
        PageableResponse<ProductDto> response = Helper.getPageableResponse(page,ProductDto.class);
        return response;
    }

    @Override
    public ProductDto createWithCategory(ProductDto productDto, String categoryId) {
       Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category with this id not found"));
        String id = UUID.randomUUID().toString();
        productDto.setProductId(id);
        productDto.setAddedDate(LocalDate.now());
        if(productDto.getQuantity()>0){
            productDto.setStock(true);
        }
        productDto.setCategory(mapper.map(category,CategoryDto.class));
        Product product = mapper.map(productDto,Product.class);
        product = productRepository.save(product);
        return mapper.map(product,ProductDto.class);
    }

    @Override
    public ProductDto updateCategory(String productId, String categoryId) {
        Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("product with this id not exist"));
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category with this id not exist!!"));
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);

        return mapper.map(savedProduct,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllOfCategory(String categoryId,int pageNumber, int pageSize,String sortBy,String sortDirection) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category does not exist!!"));
        Sort sort = (sortDirection.equalsIgnoreCase("ASC"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page =productRepository.findByCategory(category,pageable);
        return Helper.getPageableResponse(page,ProductDto.class);
    }
}

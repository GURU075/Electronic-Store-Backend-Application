package com.guru.electronic.strore.controllers;

import com.guru.electronic.strore.dtos.*;
import com.guru.electronic.strore.services.CategoryService;
import com.guru.electronic.strore.services.FileService;
import com.guru.electronic.strore.services.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @Value("${category.image.path}")
    private String imageUploadPath;

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){

        CategoryDto category = categoryService.create(categoryDto);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable String categoryId){
        CategoryDto category = categoryService.update(categoryDto,categoryId);
        return new ResponseEntity<>(category,HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId){
        categoryService.delete(categoryId);
        ApiResponseMessage message = ApiResponseMessage.builder()
                .status(HttpStatus.OK)
                .message("Category is deleted succesfully")
                .success(true)
                .build();
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategory(
            @RequestParam(value="pageNumber",defaultValue = "0",required = false)int pageNumber,
            @RequestParam(value="pageSize",defaultValue = "10",required = false)int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir){
        PageableResponse<CategoryDto> pageableResponse=categoryService.getAll(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getSingle(@PathVariable String categoryId){
        CategoryDto categoryDto = categoryService.get(categoryId);
        return new ResponseEntity<>(categoryDto,HttpStatus.OK);
    }

    @PostMapping("/image/{categoryId}")
    public ResponseEntity<ImageResponse> uploadImage(@PathVariable String categoryId,
                                                     @RequestParam("categoryImage") MultipartFile image) throws IOException {
        String imageName = fileService.uploadImage(image,imageUploadPath);

        CategoryDto categoryDto = categoryService.get(categoryId);
        categoryDto.setCoverImage(imageName);

        CategoryDto updatedCategory =categoryService.update(categoryDto,categoryId);

        ImageResponse imageResponse =ImageResponse
                .builder()
                .status(HttpStatus.OK)
                .imageName(imageName)
                .success(true)
                .message("Image uploaded successfully")
                .build();

        return new ResponseEntity<>(imageResponse,HttpStatus.OK);
    }

    @GetMapping("image/{categoryId}")
    public void serveUserImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {
        CategoryDto categoryDto =categoryService.get(categoryId);
        InputStream inputStream=fileService.getResource(imageUploadPath,categoryDto.getCoverImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(inputStream,response.getOutputStream());
    }

    @PostMapping("/{categoryId}/products")
     public ResponseEntity<ProductDto> createProductWithCategory(
             @PathVariable("categoryId") String categoryId,
             @RequestBody ProductDto dto
     ){
        ProductDto productDtoWithCategory = productService.createWithCategory(dto,categoryId);
        return new ResponseEntity<>(productDtoWithCategory,HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}/{productId}")
    public ResponseEntity<ProductDto> assignCategoryToDto(
            @PathVariable String categoryId,
            @PathVariable String productID

    ){
       ProductDto productDto = productService.updateCategory(productID,categoryId);
       return new ResponseEntity<>(productDto,HttpStatus.OK);
    }

    @GetMapping("/getAllProducts/{categoryId}")
    public ResponseEntity<PageableResponse<ProductDto>> getAllProductsOfCategory(
            @PathVariable String categoryId,
             @RequestParam(value="pageNumber",defaultValue = "0",required = false)int pageNumber,
    @RequestParam(value="pageSize",defaultValue = "10",required = false)int pageSize,
    @RequestParam(value = "sortBy",defaultValue = "title",required = false)String sortBy,
    @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir

    ){
        PageableResponse<ProductDto> productDto = productService.getAllOfCategory(categoryId,pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }

}

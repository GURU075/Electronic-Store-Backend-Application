package com.guru.electronic.strore.controllers;

import com.guru.electronic.strore.dtos.ApiResponseMessage;
import com.guru.electronic.strore.dtos.ImageResponse;
import com.guru.electronic.strore.dtos.PageableResponse;
import com.guru.electronic.strore.dtos.ProductDto;
import com.guru.electronic.strore.entities.Product;
import com.guru.electronic.strore.services.FileService;
import com.guru.electronic.strore.services.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/products")
public class ProductController {


    @Value("${product.image.path}")
    private String imagePath;
    @Autowired
    private FileService fileService;
    @Autowired
    private ProductService productService;


    //create
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto){

        ProductDto product= productService.create(productDto);
        return new ResponseEntity<>(product, HttpStatus.CREATED);

    }

    //update
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto, @PathVariable String productId){
        ProductDto product = productService.update(productDto,productId);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable String productId){
         productService.delete(productId);
         ApiResponseMessage apiResponseMessage = ApiResponseMessage
                 .builder()
                 .message("product deleted successfully")
                 .success(true)
                 .status(HttpStatus.OK)
                 .build();

         return  new ResponseEntity<>(apiResponseMessage,HttpStatus.OK);
    }

    //getAll
    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>> getAll(@RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
                                               @RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize,
                                               @RequestParam(value = "sortBy",defaultValue = "title",required = false)String sortBy,
                                               @RequestParam(value = "sortDirection",defaultValue = "asc",required = false)String sortDirection){
        PageableResponse<ProductDto> response = productService.getAll(pageNumber, pageSize, sortBy, sortDirection);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //getSingle
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getSingle(@PathVariable String productId){
        ProductDto productDto = productService.getById(productId);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }

    //getAllLive
    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>> getALlLive(@RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
                                                                   @RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize,
                                                                   @RequestParam(value = "sortBy",defaultValue = "live",required = false)String sortBy,
                                                                   @RequestParam(value = "sortDirection",defaultValue = "asc",required = false)String sortDirection){
        return new ResponseEntity<>(productService.getAllLive(pageNumber,pageSize,sortBy,sortDirection),HttpStatus.OK);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<PageableResponse<ProductDto>> search(@PathVariable String keyword,
                                                               @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
                                                                   @RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize,
                                                                   @RequestParam(value = "sortBy",defaultValue = "title",required = false)String sortBy,
                                                                   @RequestParam(value = "sortDirection",defaultValue = "asc",required = false)String sortDirection){
        return new ResponseEntity<>(productService.searchByTitle(keyword,pageNumber,pageSize,sortBy,sortDirection),HttpStatus.OK);
    }

    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadImage(
            @PathVariable String productId,
            @RequestParam("productImage") MultipartFile productImage
            ) throws IOException {
        String imageName =fileService.uploadImage(productImage,imagePath);
       ProductDto productDto =productService.getById(productId);
       productDto.setProductImageName(imageName);
       productService.update(productDto,productId);
       ImageResponse response = ImageResponse
               .builder()
               .imageName(imageName)
               .message("Image uploaded successfully")
               .success(true)
               .status(HttpStatus.CREATED)
               .build();
       return new ResponseEntity<>(response,HttpStatus.CREATED);

    }

    @GetMapping("/image/{productId}")
    public void serveImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
        ProductDto productDto = productService.getById(productId);
        String imageName=productDto.getProductImageName();
        InputStream inputStream = fileService.getResource(imagePath,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(inputStream,response.getOutputStream());
    }
}

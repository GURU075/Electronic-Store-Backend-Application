package com.guru.electronic.strore.controllers;


import com.guru.electronic.strore.dtos.AddItemToCartRequest;
import com.guru.electronic.strore.dtos.ApiResponseMessage;
import com.guru.electronic.strore.dtos.CartDto;
import com.guru.electronic.strore.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemToCart(@RequestBody AddItemToCartRequest request, @PathVariable int userId){
        CartDto cartDto =cartService.addItemToCart(userId, request);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<ApiResponseMessage> removeItemsFromCarts(@PathVariable int userId,@PathVariable int itemId){
         cartService.removeItemFromCart(userId,itemId);
         ApiResponseMessage responseMessage =ApiResponseMessage.builder()
                 .message("Item is removed")
                 .success(true)
                 .status(HttpStatus.OK)
                 .build();
         return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> clearCart(@PathVariable int userId){
        cartService.clearCart(userId);
        ApiResponseMessage responseMessage =ApiResponseMessage.builder()
                .message("Now cart is empty")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart( @PathVariable int userId){
        CartDto cartDto =cartService.getCartByUSer(userId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }
}

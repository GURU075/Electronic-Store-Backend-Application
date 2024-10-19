package com.guru.electronic.strore.services;

import com.guru.electronic.strore.dtos.AddItemToCartRequest;
import com.guru.electronic.strore.dtos.CartDto;

public interface CartService {

    //add items to cart:
    //case 1 cart for user is not available: we will create the cart and then add the items
    //case 2 - if cart available then directly add the items

    CartDto addItemToCart(int userId, AddItemToCartRequest request);


    //remove item from cart;

    void removeItemFromCart(int userId,int cartItem);

    void clearCart(int userId);

    CartDto getCartByUSer(int userId);

}

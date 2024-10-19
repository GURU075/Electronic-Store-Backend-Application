package com.guru.electronic.strore.serviceImpls;

import com.guru.electronic.strore.dtos.AddItemToCartRequest;
import com.guru.electronic.strore.dtos.CartDto;
import com.guru.electronic.strore.dtos.CartItemDto;
import com.guru.electronic.strore.entities.Cart;
import com.guru.electronic.strore.entities.CartItem;
import com.guru.electronic.strore.entities.Product;
import com.guru.electronic.strore.entities.User;
import com.guru.electronic.strore.exceptions.BadApiRequestException;
import com.guru.electronic.strore.exceptions.ResourceNotFoundException;
import com.guru.electronic.strore.repositories.CartItemRepository;
import com.guru.electronic.strore.repositories.CartRepository;
import com.guru.electronic.strore.repositories.ProductRepository;
import com.guru.electronic.strore.repositories.UserRepository;
import com.guru.electronic.strore.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final ProductRepository productRepository;
    private final ModelMapper mapper;

    public CartServiceImpl(ProductRepository productRepository, ModelMapper mapper){
        this.productRepository=productRepository;
        this.mapper = mapper;
    }

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;




    @Override
    public CartDto addItemToCart(int userId, AddItemToCartRequest request) {
        int quantity= request.getQuantity();
        String productId =request.getProductId();

        if(quantity<=0){
            throw new BadApiRequestException("Requested quantity is not valid");
        }

        Product  product=productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("product with this id not found!!"));
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("NOT Found user!!"));
        Cart cart =null;
        try{
            cart =cartRepository.findByUser(user).get();

        }catch (NoSuchElementException e){
            cart =new Cart();
            cart.setCreatedAt(LocalDate.now());
            cart.setCartId(UUID.randomUUID().toString());
        }

        //if cart item already present update
        AtomicBoolean updated = new AtomicBoolean(false);
        List<CartItem> items =cart.getCartItems();
//        List<CartItem> updatedItems =items.stream().map(item->{
//
//            if (item.getProduct().getProductId().equals(productId)) {
//                //item already prsent in the cart
//                item.setQuantity(quantity);
//                item.setTotalPrice(quantity*product.getPrice());
//                updated.set(true);
//            }
//
//            return item;
//        }).collect(Collectors.toList());

        items.forEach(item -> {
            if (item.getProduct().getProductId().equals(productId)) {
                item.setQuantity(quantity);
                item.setTotalPrice(quantity * product.getPrice());
                updated.set(true);
            }
        });


        cart.setCartItems(items);

       if(!updated.get()){
           CartItem cartItem =CartItem.builder()
                   .quantity(quantity)
                   .totalPrice(quantity*product.getPrice())
                   .cart(cart)
                   .product(product)
                   .build();

           cart.getCartItems().add(cartItem);
       }
        cart.setUser(user);
        Cart updatedCart = cartRepository.save(cart);
        return mapper.map(updatedCart,CartDto.class);
    }

    @Override
    public void removeItemFromCart(int userId, int cartItem) {
//        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("UserDoesNotExist"));
        CartItem item = cartItemRepository.findById(cartItem).orElseThrow(()-> new ResourceNotFoundException("cart Item not found"));
        cartItemRepository.delete(item);

    }

    @Override
    public void clearCart(int userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not exist!!"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(()->new ResourceNotFoundException("Cart Not created!!"));
        cart.getCartItems().clear();
        cartRepository.save(cart);

    }

    @Override
    public CartDto getCartByUSer(int userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not exist!!"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(()->new ResourceNotFoundException("Cart Not created!!"));
        return mapper.map(cart,CartDto.class);
    }
}

package com.guru.electronic.strore.serviceImpls;

import com.guru.electronic.strore.dtos.CreateOrderRequest;
import com.guru.electronic.strore.dtos.OrderDto;
import com.guru.electronic.strore.dtos.PageableResponse;
import com.guru.electronic.strore.entities.*;
import com.guru.electronic.strore.exceptions.BadApiRequestException;
import com.guru.electronic.strore.exceptions.ResourceNotFoundException;
import com.guru.electronic.strore.helper.Helper;
import com.guru.electronic.strore.repositories.CartRepository;
import com.guru.electronic.strore.repositories.OrderRepository;
import com.guru.electronic.strore.repositories.UserRepository;
import com.guru.electronic.strore.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;

    public OrderServiceImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public OrderDto createOrder(CreateOrderRequest orderDto) {
         int userId = orderDto.getUserId();
        User user =userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user not found!"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(()->new ResourceNotFoundException("Cart not found!"));
        List<CartItem> cartItems =cart.getCartItems();
        if(cartItems.size()<=0){
            throw new BadApiRequestException("Invalid number of items in cart");
        }
        Order order =Order.builder()
                .billingName(orderDto.getBillingName())
                .billingPhone(orderDto.getBillingPhone())
                .billingAddress(orderDto.getBillingAddress())
                .orderedDate(LocalDate.now())
                .deliveredDate(null)
                .paymentStatus(orderDto.getPaymentStatus())
                .orderStatus(orderDto.getOrderStatus())
                .orderId(UUID.randomUUID().toString())
                .user(user)
                .build();

        AtomicReference<Integer> orderAmount = new AtomicReference<>(0);
        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
            //cartitems->orderitems
            OrderItem orderItem = OrderItem.builder()
                    .quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .totalPrice(cartItem.getTotalPrice())
                    .order(order)
                    .build();
            return  orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        order.setOrderAmount(orderAmount.get());
        cart.getCartItems().clear();
        cartRepository.save(cart);
        Order savedOrder = orderRepository.save(order);
        return mapper.map(savedOrder,OrderDto.class);
    }

    @Override
    public void removeOrder(String orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(()->new ResourceNotFoundException("order not found"));
        orderRepository.delete(order);

    }

    @Override
    public PageableResponse<OrderDto> getOrder(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("ASC")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Order> page = orderRepository.findAll(pageable);
        return Helper.getPageableResponse(page,OrderDto.class);
    }

    @Override
    public List<OrderDto> getOrdersOfUser(int userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not found"));
        List<Order> orders = orderRepository.findByUser(user);
        List<OrderDto> orderDtos= orders.stream().map(order->mapper.map(order,OrderDto.class)).collect(Collectors.toList());
        return orderDtos;
    }
}

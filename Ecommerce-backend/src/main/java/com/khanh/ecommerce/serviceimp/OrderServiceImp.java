package com.khanh.ecommerce.serviceimp;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.khanh.ecommerce.DTO.OrderDetailDTO;
import com.khanh.ecommerce.DTO.OrderRequestDTO;
import com.khanh.ecommerce.DTO.OrderResponseDTO;
import com.khanh.ecommerce.entity.Cart;
import com.khanh.ecommerce.entity.CartLineItem;
import com.khanh.ecommerce.entity.Order;
import com.khanh.ecommerce.entity.OrderDetail;
import com.khanh.ecommerce.entity.User;
import com.khanh.ecommerce.entity.VariantProduct;
import com.khanh.ecommerce.repository.CartLineItemRepository;
import com.khanh.ecommerce.repository.CartRepository;
import com.khanh.ecommerce.repository.OrderDetailRepository;
import com.khanh.ecommerce.repository.OrderRepository;
import com.khanh.ecommerce.repository.UserRepository;
import com.khanh.ecommerce.repository.VariantProductRepository;
import com.khanh.ecommerce.service.OrderService;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImp implements OrderService {
	@Autowired
	private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartLineItemRepository cartLineItemRepository;
    @Autowired
    private VariantProductRepository variantProductRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    
    @Transactional
    public OrderResponseDTO placeOrder(String username, OrderRequestDTO request) {
    	 User user = userRepository.findByUsername(username)
                 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
         Cart cart = cartRepository.findByUser(user)
                 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));
         
         List<CartLineItem> activeItems = cart.getItems().stream()
        		 .collect(Collectors.toList());
         if(activeItems.isEmpty()) {
        	 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart is empty");
         }
         
         for(CartLineItem item : activeItems) {
        	 VariantProduct variant = variantProductRepository.findById(item.getVariantProduct().getId())
                     .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Variant not found"));
             double latestPrice = variant.getPrice();
             item.setUnitPrice(latestPrice);
             item.setTotalPrice(item.getQuantity() * latestPrice);
             cartLineItemRepository.save(item);
         }
         
         double totalPrice =  activeItems.stream()
        		 .mapToDouble(CartLineItem::getTotalPrice)
        		 .sum();
         
         Order order = Order.builder()
        		 .user(user)
        		 .shippingAddress(request.getShippingAddress())
        		 .recipientName(request.getRecipientName())
        		 .phone(request.getPhone())
        		 .status("PENDING")
        		 .totalPrice(totalPrice)
        		 .build();
         order = orderRepository.save(order);
         final Order savedOrder = order;
         
         List<OrderDetail> orderDetails = activeItems.stream()
        		 .map(item -> {
        			 VariantProduct vp = item.getVariantProduct();
        			 return OrderDetail.builder()
                             .order(savedOrder)
                             .variantProduct(vp)
                             .sku(vp.getSku())
                             .productName(vp.getProduct().getName())
                             .color(vp.getColor())
                             .size(vp.getSize())
                             .quantity(item.getQuantity())
                             .unitPrice(item.getUnitPrice())
                             .totalPrice(item.getTotalPrice())
                             .build();
        		 })
        		 .collect(Collectors.toList());
         orderDetailRepository.saveAll(orderDetails);
         order.setOrderDetails(orderDetails);
         orderRepository.save(order);
         
         activeItems.forEach(item -> cart.getItems().remove(item));
         cartRepository.save(cart);
         cartLineItemRepository.deleteAll(activeItems);

         
         return OrderResponseDTO.builder()
                 .orderId(order.getId())
                 .username(user.getUsername())
                 .orderDate(order.getOrderDate())
                 .totalPrice(order.getTotalPrice())
                 .shippingAddress(order.getShippingAddress())
                 .recipientName(order.getRecipientName())
                 .phone(order.getPhone())
                 .orderDetails(orderDetails.stream()
                         .map(od -> OrderDetailDTO.builder()
                                 .id(od.getId())
                                 .productName(od.getProductName())
                                 .color(od.getColor())
                                 .size(od.getSize())
                                 .sku(od.getSku())
                                 .quantity(od.getQuantity())
                                 .unitPrice(od.getUnitPrice())
                                 .totalPrice(od.getTotalPrice())
                                 .build())
                         .collect(Collectors.toList()))
                 .build();
    }
}

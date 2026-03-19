package com.khanh.ecommerce.DTO;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDTO {
    private Long orderId;
    private String username;
    private LocalDateTime orderDate;
    private Double totalPrice;
    private String shippingAddress;
    private String recipientName;
    private String phone;
    private List<OrderDetailDTO> orderDetails;
}

package com.khanh.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.khanh.ecommerce.entity.Cart;
import com.khanh.ecommerce.entity.CartLineItem;
import com.khanh.ecommerce.entity.VariantProduct;

public interface CartLineItemRepository extends JpaRepository<CartLineItem, Long> {
    Optional<CartLineItem> findByCartAndVariantProduct(Cart cart, VariantProduct variantProduct);
}

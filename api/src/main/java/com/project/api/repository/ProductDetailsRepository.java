package com.project.api.repository;

import com.project.api.entity.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductDetailsRepository extends JpaRepository<ProductDetails, Long> {
    List<ProductDetails> findByBrandContaining(String brand);
    List<ProductDetails> findByBrandContainingOrIdIn(String brand, List<Long> ids);
}


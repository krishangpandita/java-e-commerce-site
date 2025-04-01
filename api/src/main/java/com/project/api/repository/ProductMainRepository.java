package com.project.api.repository;

import com.project.api.entity.ProductMain;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductMainRepository extends JpaRepository<ProductMain, Long> {
    List<ProductMain> findByTitleContaining(String title);
    List<ProductMain> findByTitleContainingOrIdIn(String title, List<Long> ids);
}


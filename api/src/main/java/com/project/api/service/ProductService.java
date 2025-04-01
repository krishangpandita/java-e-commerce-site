package com.project.api.service;

import com.project.api.entity.ProductMain;
import com.project.api.request.CouponRequest;
import com.project.api.request.ProductRequest;
import com.project.api.response.CouponResponse;
import com.project.api.response.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAllProducts();
    ProductResponse findProductById(Long id);
    ProductResponse createProductMain(ProductRequest request);
    ProductResponse updateProductMain(ProductRequest request);
    void deleteProductMain(Long productId);
    List<ProductResponse> searchProductsByTitle(String title);
    List<ProductResponse> searchProductsByBrand(String brand);
    List<ProductResponse> searchProducts(String query);

}


package com.project.api.controller;

import com.project.api.exception.ResourceNotFoundException;
import com.project.api.request.CouponRequest;
import com.project.api.request.LoginRequest;
import com.project.api.request.ProductRequest;
import com.project.api.request.UserInfoRequest;
import com.project.api.response.CouponResponse;
import com.project.api.response.LoginResponse;
import com.project.api.response.ProductResponse;
import com.project.api.response.UserInfoResponse;
import com.project.api.service.LoginService;
import com.project.api.service.ProductService;
import com.project.api.service.CouponService;
import com.project.api.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductAPIController {

    @Autowired
    private final ProductService productService;

    @Autowired
    private final CouponService couponService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private LoginService loginService;

    public ProductAPIController(ProductService productService, CouponService couponService) {
        this.productService = productService;
        this.couponService = couponService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long productId) {
        ProductResponse product = productService.findProductById(productId);
        if (product == null) {
            throw new ResourceNotFoundException("Product with ID " + productId + " not found");
        }
        return ResponseEntity.ok(product);
    }

    //    @GetMapping("/search")
//    public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String query) {
//        List<ProductResponse> responses = productService.searchProducts(query);
//        return ResponseEntity.ok(responses);
//    }
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        ProductResponse response = productService.createProductMain(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long productId, @Valid @RequestBody ProductRequest request) {
        ProductResponse existingProduct = productService.findProductById(productId);
        if (existingProduct == null) {
            throw new ResourceNotFoundException("Product with ID " + productId + " not found");
        }
        request.setId(productId); // Ensure the ID is set in the request
        ProductResponse updatedProduct = productService.updateProductMain(request);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        ProductResponse product = productService.findProductById(productId);
        if (product == null) {
            throw new ResourceNotFoundException("Product with ID " + productId + " not found");
        }
        productService.deleteProductMain(productId);
        return ResponseEntity.ok("Product Deleted Successfully");
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> searchProducts(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "brand", required = false) String brand) {
        if (title != null && !title.isEmpty()) {
            return ResponseEntity.ok(productService.searchProductsByTitle(title));
        } else if (brand != null && !brand.isEmpty()) {
            return ResponseEntity.ok(productService.searchProductsByBrand(brand));
        } else {
            return ResponseEntity.ok(productService.getAllProducts());
        }
    }

    @PostMapping("/coupons")
    public ResponseEntity<CouponResponse> createCoupon(@Valid @RequestBody CouponRequest request) {
        CouponResponse response = couponService.createCoupon(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/coupons/{couponId}")
    public ResponseEntity<CouponResponse> updateCoupon(@PathVariable Long couponId, @Valid @RequestBody CouponRequest request) {
        CouponResponse response = couponService.updateCoupon(couponId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/coupons/{couponId}")
    public ResponseEntity<String> deleteCoupon(@PathVariable Long couponId) {
        couponService.deleteCoupon(couponId);
        return ResponseEntity.ok("Coupon Deleted Successfully");
    }

    @GetMapping("/{productId}/coupons")
    public ResponseEntity<List<CouponResponse>> getCouponsByProductId(@PathVariable Long productId) {
        List<CouponResponse> coupons = couponService.getCouponsByProductId(productId);
        return ResponseEntity.ok(coupons);
    }

    @GetMapping("/coupons/{couponId}")
    public ResponseEntity<CouponResponse> getCouponById(@PathVariable Long couponId) {
        CouponResponse coupon = couponService.getCouponById(couponId);
        if (coupon == null) {
            throw new ResourceNotFoundException("Coupon with ID " + couponId + " not found");
        }
        return ResponseEntity.ok(coupon);
    }

    @GetMapping("/coupons")
    public ResponseEntity<List<CouponResponse>> getAllCoupons() {
        List<CouponResponse> coupons = couponService.getAllCoupons();
        return ResponseEntity.ok(coupons);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserInfoRequest userInfoRequest) {
        try {
            UserInfoResponse savedUser = userInfoService.registerUser(userInfoRequest);
            return ResponseEntity.ok(savedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = loginService.loginUser(loginRequest);
        if (loginResponse.getMessage().equals("Login successful")) {
            return ResponseEntity.ok(loginResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginResponse);
        }
    }
}


package com.project.api.service;

import com.project.api.request.CouponRequest;
import com.project.api.response.CouponResponse;

import java.util.List;

public interface CouponService {
    // Coupon methods
    List<CouponResponse> getAllCoupons();

    // Method to get a coupon by its ID
    CouponResponse getCouponById(Long id);
    CouponResponse updateCoupon(Long id, CouponRequest request);

    CouponResponse createCoupon(CouponRequest request);
    void deleteCoupon(Long id);
    List<CouponResponse> getCouponsByProductId(Long productId);
}

package com.project.api.service.impl;

import com.project.api.entity.Coupon;
import com.project.api.entity.ProductMain;
import com.project.api.repository.CouponRepository;
import com.project.api.repository.ProductMainRepository;
import com.project.api.request.CouponRequest;
import com.project.api.response.CouponResponse;
import com.project.api.service.CouponService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepo;

    @Autowired
    private ProductMainRepository productMainRepo;

    @Override
    public List<CouponResponse> getAllCoupons() {
        List<Coupon> coupons = couponRepo.findAll();
        return coupons.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CouponResponse createCoupon(CouponRequest request) {
        ProductMain product = productMainRepo.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Coupon coupon = new Coupon();
        BeanUtils.copyProperties(request, coupon);
        coupon.setProductMain(product);

        Coupon savedCoupon = couponRepo.save(coupon);

        return convertToResponse(savedCoupon);
    }

    @Override
    public CouponResponse updateCoupon(Long id, CouponRequest request) {
        Coupon coupon = couponRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupon not found"));

        BeanUtils.copyProperties(request, coupon);
        Coupon updatedCoupon = couponRepo.save(coupon);

        return convertToResponse(updatedCoupon);
    }

    @Override
    public void deleteCoupon(Long id) {
        couponRepo.deleteById(id);
    }

    @Override
    public List<CouponResponse> getCouponsByProductId(Long productId) {
        List<Coupon> coupons = couponRepo.findAll().stream()
                .filter(coupon -> coupon.getProductMain().getId().equals(productId))
                .collect(Collectors.toList());

        return coupons.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CouponResponse getCouponById(Long id) {
        Coupon coupon = couponRepo.findById(id).orElseThrow(() -> new RuntimeException("Coupon not found"));
        return convertToResponse(coupon);
    }

    private CouponResponse convertToResponse(Coupon coupon) {
        CouponResponse response = new CouponResponse();
        BeanUtils.copyProperties(coupon, response);
        response.setProductId(coupon.getProductMain().getId());
        response.setProductTitle(coupon.getProductMain().getTitle());  // Set the product title
        return response;
    }
}




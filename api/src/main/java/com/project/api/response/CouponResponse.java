package com.project.api.response;

import com.project.api.entity.Coupon;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CouponResponse {
    private Long id;
    private String code;
    private Long discountPercentage;
    private Long productId;

    private String productTitle;


//    public CouponResponse(Coupon coupon) {
//        this.id = coupon.getId();
//        this.code = coupon.getCode();
//        this.discountPercentage = coupon.getDiscountPercentage();
//        this.productId = coupon.getProductMain().getId();
////        this.productTitle = coupon.getProductMain().getTitle();
//    }
}


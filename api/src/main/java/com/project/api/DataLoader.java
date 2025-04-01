//package com.project.api;
//
//import com.project.api.entity.Coupon;
//import com.project.api.entity.ProductMain;
//import com.project.api.repository.CouponRepository;
//import com.project.api.repository.ProductMainRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Random;
//
//@Component
//public class DataLoader implements CommandLineRunner {
//
//    private final CouponRepository couponRepository;
//    private final ProductMainRepository productMainRepository;
//
//    public DataLoader(CouponRepository couponRepository, ProductMainRepository productMainRepository) {
//        this.couponRepository = couponRepository;
//        this.productMainRepository = productMainRepository;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        List<ProductMain> products = productMainRepository.findAll();
//        Random random = new Random();
//
//        for (ProductMain product : products) {
//            for (int i = 0; i < 5; i++) { // Generating 5 coupons per product
//                Coupon coupon = new Coupon();
//                coupon.setCode(generateRandomCouponCode());
//                coupon.setDiscountPercentage((long) random.nextInt(30) + 1); // Random discount between 1% and 30%
//                coupon.setProductMain(product);
//                couponRepository.save(coupon);
//            }
//        }
//    }
//
//    private String generateRandomCouponCode() {
//        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
//        StringBuilder couponCode = new StringBuilder();
//        Random rnd = new Random();
//        while (couponCode.length() < 10) { // Coupon code length
//            int index = (int) (rnd.nextFloat() * chars.length());
//            couponCode.append(chars.charAt(index));
//        }
//        return couponCode.toString();
//    }
//}
//

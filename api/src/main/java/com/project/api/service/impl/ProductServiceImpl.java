package com.project.api.service.impl;

import com.project.api.entity.Coupon;
import com.project.api.entity.ProductDetails;
import com.project.api.entity.ProductMain;
import com.project.api.entity.ProductStock;
import com.project.api.repository.CouponRepository;
import com.project.api.repository.ProductDetailsRepository;
import com.project.api.repository.ProductMainRepository;
import com.project.api.repository.ProductStockRepository;
import com.project.api.request.CouponRequest;
import com.project.api.request.ProductRequest;
import com.project.api.response.CouponResponse;
import com.project.api.response.ProductResponse;
import com.project.api.service.ProductService;
import com.project.api.service.CouponService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductMainRepository productMainRepo;
    @Autowired
    private ProductStockRepository productStockRepo;
    @Autowired
    private ProductDetailsRepository productDetailsRepo;

    @Autowired
    private CouponRepository couponRepo;

    @Override
    public List<ProductResponse> getAllProducts() {
// Fetch all products, details, and stocks
        List<ProductMain> products = productMainRepo.findAll();
        List<ProductDetails> details = productDetailsRepo.findAll();
        List<ProductStock> stocks = productStockRepo.findAll();

        List<ProductResponse> responses = IntStream.range(0, Math.min(products.size(), Math.min(details.size(), stocks.size())))
                .mapToObj(i -> {
                    ProductMain product = products.get(i);
                    ProductDetails detail = details.get(i);
                    ProductStock stock = stocks.get(i);

                    ProductResponse response = new ProductResponse();
                    response.setId(product.getId());
                    response.setTitle(product.getTitle());
                    response.setPrice(detail.getPrice());
                    response.setRating(detail.getRating());
                    response.setStock(stock.getStock());
                    response.setDiscountPercentage(detail.getDiscountPercentage());
                    response.setBrand(detail.getBrand());
                    response.setThumbnail(detail.getThumbnail());
                    response.setImages(detail.getImages());

                    return response;
                })
                .collect(Collectors.toList());

        return responses;
    }

    @Override
    public ProductResponse findProductById(Long id) {
        ProductMain product = productMainRepo.findById(id).orElse(null);
        ProductDetails detail = productDetailsRepo.findById(id).orElse(null);
        ProductStock stock = productStockRepo.findById(id).orElse(null);

        if (product != null && detail != null && stock != null) {
            ProductResponse response = new ProductResponse();
            response.setId(product.getId());
            response.setTitle(product.getTitle());
            response.setPrice(detail.getPrice());
            response.setRating(detail.getRating());
            response.setStock(stock.getStock());
            response.setDiscountPercentage(detail.getDiscountPercentage());
            response.setBrand(detail.getBrand());
            response.setThumbnail(detail.getThumbnail());
            response.setImages(detail.getImages());
            return response;
        }
//        } else {
//            return null;
//        }
            return null;
    }

    @Override
    public ProductResponse createProductMain(ProductRequest request){
        ProductMain product = new ProductMain();
        ProductDetails details =  new ProductDetails();
        ProductStock stock = new ProductStock();
        BeanUtils.copyProperties(request,product);
        BeanUtils.copyProperties(request,details);
        BeanUtils.copyProperties(request,stock);
        ProductMain main = productMainRepo.save(product);
        productDetailsRepo.save(details);
        productStockRepo.save(stock);
        ProductResponse response = new ProductResponse();
        response.setId(main.getId());
        response.setTitle(main.getTitle());
        response.setBrand(details.getBrand());
        response.setPrice(details.getPrice());
        response.setRating(details.getRating());
        response.setStock(stock.getStock());
        response.setDiscountPercentage(details.getDiscountPercentage());
        response.setBrand(details.getBrand());
        response.setThumbnail(details.getThumbnail());
        response.setImages(details.getImages());

        return response;
    }

    @Override
    public ProductResponse updateProductMain(ProductRequest request) {
        ProductMain product = productMainRepo.findById(request.getId()).orElse(null);
        if (product == null) return null;

        ProductDetails details = productDetailsRepo.findById(request.getId()).orElse(null);
        ProductStock stock = productStockRepo.findById(request.getId()).orElse(null);

        BeanUtils.copyProperties(request, product);
        BeanUtils.copyProperties(request, details);
        BeanUtils.copyProperties(request, stock);

        productMainRepo.save(product);
        productDetailsRepo.save(details);
        productStockRepo.save(stock);

        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setTitle(product.getTitle());
        response.setPrice(details.getPrice());
        response.setRating(details.getRating());
        response.setStock(stock.getStock());
        response.setDiscountPercentage(details.getDiscountPercentage());
        response.setBrand(details.getBrand());
        response.setThumbnail(details.getThumbnail());
        response.setImages(details.getImages());

        return response;
    }


    @Override
    public void deleteProductMain(Long productId) {
        productMainRepo.deleteById(productId);
        productDetailsRepo.deleteById(productId);
        productStockRepo.deleteById(productId);
    }

    @Override
    public List<ProductResponse> searchProductsByTitle(String title) {
        List<ProductMain> products = productMainRepo.findByTitleContaining(title);
        return products.stream()
                .map(this::convertToProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> searchProductsByBrand(String brand) {
        List<ProductDetails> details = productDetailsRepo.findByBrandContaining(brand);
        return details.stream()
                .map(detail -> {
                    ProductMain product = productMainRepo.findById(detail.getId()).orElse(null);
                    ProductStock stock = productStockRepo.findById(detail.getId()).orElse(null);
                    return convertToProductResponse(product, detail, stock);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> searchProducts(String query) {
        List<ProductMain> productsByTitle = productMainRepo.findByTitleContaining(query);
        List<ProductDetails> detailsByBrand = productDetailsRepo.findByBrandContaining(query);

        List<Long> productIds = productsByTitle.stream().map(ProductMain::getId).collect(Collectors.toList());
        List<Long> detailIds = detailsByBrand.stream().map(ProductDetails::getId).collect(Collectors.toList());

        productIds.addAll(detailIds);

        List<ProductMain> products = productMainRepo.findByTitleContainingOrIdIn(query, productIds);
        List<ProductDetails> details = productDetailsRepo.findByBrandContainingOrIdIn(query, productIds);

        return convertToProductResponse(products, details);
    }

    private List<ProductResponse> convertToProductResponse(List<ProductMain> products, List<ProductDetails> details) {
        List<ProductStock> stocks = productStockRepo.findAll();
        return products.stream().map(product -> {
            ProductDetails detail = details.stream().filter(d -> d.getId().equals(product.getId())).findFirst().orElse(null);
            ProductStock stock = stocks.stream().filter(s -> s.getId().equals(product.getId())).findFirst().orElse(null);
            return convertToProductResponse(product, detail, stock);
        }).collect(Collectors.toList());
    }

    private ProductResponse convertToProductResponse(ProductMain product) {
        if (product == null) return null;
        ProductDetails detail = productDetailsRepo.findById(product.getId()).orElse(null);
        ProductStock stock = productStockRepo.findById(product.getId()).orElse(null);
        return convertToProductResponse(product, detail, stock);
    }

    private ProductResponse convertToProductResponse(ProductMain product, ProductDetails detail, ProductStock stock) {
        if (product == null) return null;
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setTitle(product.getTitle());
        if (detail != null) {
            response.setPrice(detail.getPrice());
            response.setRating(detail.getRating());
            response.setDiscountPercentage(detail.getDiscountPercentage());
            response.setBrand(detail.getBrand());
            response.setThumbnail(detail.getThumbnail());
            response.setImages(detail.getImages());
        }
        if (stock != null) {
            response.setStock(stock.getStock());
        }
        return response;
    }
}

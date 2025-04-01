package com.project.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_details")
public class ProductDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "price")
    private Long price;
    @Column(name = "discountPercentage")
    private Long discountPercentage;
    @Column(name = "rating")
    private Long rating;
    @Column(name = "brand")
    private String brand;
    @Column(name = "thumbnail")
    private String thumbnail;
    @ElementCollection
    @Column(name = "image", length = 16000)
    private List<String> images;


//    @OneToOne
//    @JoinColumn(name = "id")
//    @JsonIgnore
//    private ProductMain productMain;
//
//    private Long price;
//    private Long discountPercentage;
//    private Long rating;
//    private String brand;
//    private String thumbnail;
//
//    @ElementCollection
//    @Column(name = "image", length = 16000)
//    private List<String> images;
//
//
//    public ProductDetails(){
//    }
//
//
//    public ProductDetails(Long id, ProductMain productMain, Long price, Long discountPercentage, Long rating, String brand, String thumbnail, List<String> images) {
//        this.id = id;
//        this.productMain = productMain;
//        this.price = price;
//        this.discountPercentage = discountPercentage;
//        this.rating = rating;
//        this.brand = brand;
//        this.thumbnail = thumbnail;
//        this.images = images;
//
//    }
//
//
//    // Getters and Setters
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public ProductMain getProductMain() {
//        return productMain;
//    }
//
//    public void setProductMain(ProductMain productMain) {
//        this.productMain = productMain;
//    }
//
//    public Long getPrice() {
//        return price;
//    }
//
//    public void setPrice(Long price) {
//        this.price = price;
//    }
//
//    public Long getDiscountPercentage() {
//        return discountPercentage;
//    }
//
//    public void setDiscountPercentage(Long discountPercentage) {
//        this.discountPercentage = discountPercentage;
//    }
//
//    public Long getRating() {
//        return rating;
//    }
//
//    public void setRating(Long rating) {
//        this.rating = rating;
//    }
//
//    public String getBrand() {
//        return brand;
//    }
//
//    public void setBrand(String brand) {
//        this.brand = brand;
//    }
//
//    public String getThumbnail() {
//        return thumbnail;
//    }
//
//    public void setThumbnail(String thumbnail) {
//        this.thumbnail = thumbnail;
//    }
//
//    public List<String> getImages() {
//        return images;
//    }
//
//    public void setImages(List<String> images) {
//        this.images = images;
//    }
//

    // ...
}

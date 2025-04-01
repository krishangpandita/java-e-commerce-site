package com.project.api.entity;

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
@Table(name = "product_main")
public class ProductMain {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "productMain", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Coupon> coupons;
}



//    @OneToOne(mappedBy = "productMain", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnore
//    private ProductStock productStock;
//
//    @OneToOne(mappedBy = "productMain", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnore
//    private ProductDetails productDetails;


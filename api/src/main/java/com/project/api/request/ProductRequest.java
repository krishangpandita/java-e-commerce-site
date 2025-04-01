package com.project.api.request;

//import jakarta.persistence.Column;
//import jakarta.persistence.ElementCollection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private Long id;
    private String title;
    private Long stock;
    private Long price;
    private Long discountPercentage;
    private Long rating;
    private String brand;
    private String thumbnail;
    private List<String> images;
}

package com.project.api.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
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

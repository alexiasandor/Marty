package com.example.martybackend.dtos;

import com.example.martybackend.entity.Ord;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private Long productId;
    private String productName;
    private int price;
    private String description;
    private Ord ord;
}

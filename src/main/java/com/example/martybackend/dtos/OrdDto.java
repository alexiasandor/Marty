package com.example.martybackend.dtos;

import com.example.martybackend.entity.Product;
import com.example.martybackend.entity.Sale;
import com.example.martybackend.entity.User;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdDto {
    private Long ord_Id;
    private int tableNr;
    private float price;
    private User user;
    private Sale sale;
    private List<Product> productList;
}

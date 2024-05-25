package com.example.martybackend.dtos;

import com.example.martybackend.entity.Ord;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleDto {
    private Long saleId;
    private String saleName;
    private Float percent;
    private List<Ord> ordersList;
}

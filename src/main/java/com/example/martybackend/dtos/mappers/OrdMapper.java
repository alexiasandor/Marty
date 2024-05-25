package com.example.martybackend.dtos.mappers;

import com.example.martybackend.dtos.OrdDto;
import com.example.martybackend.entity.Ord;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OrdMapper {
    public static Ord fromDto(OrdDto ordDto){
        return Ord.builder()
                .ord_Id(ordDto.getOrd_Id())
                .tableNr(ordDto.getTableNr())
                .price(ordDto.getPrice())
                .user(ordDto.getUser())
                .sale(ordDto.getSale())
                .productList(ordDto.getProductList())
                .build();//
    }
    public static OrdDto toEntity(Ord ord){
        return OrdDto.builder()
                .ord_Id(ord.getOrd_Id())
                .tableNr(ord.getTableNr())
                .price(ord.getPrice())
                .user(ord.getUser())
                .sale(ord.getSale())
                .productList(ord.getProductList())
                .build();//
    }
}

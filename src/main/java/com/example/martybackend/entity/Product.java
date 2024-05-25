package com.example.martybackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @Column(name = "productName", nullable = false)
    private  String productName;
    @Column(name = "price", nullable = false)
    private int price;
    @Column(name = "description", nullable = false)
    private String description;

    @ManyToMany(mappedBy = "productList", cascade = CascadeType.ALL)
   // @JoinColumn(name = "order_Id")
    @JsonBackReference  // ca sa nu afiseze recursiv
    List<Ord> ord;
}

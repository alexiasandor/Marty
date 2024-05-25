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
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "saleId", nullable = false)
    private Long saleId;
    @Column(name = "saleName", nullable = false)
    private String saleName;
    @Column (name = "percent", nullable = false)
    private Float percent;

    @OneToMany( mappedBy = "sale")
    @JsonBackReference
    private List<Ord> ordersList;
}

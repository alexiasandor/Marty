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
public class Ord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ord_Id;
    @Column(name = "table_Nr", nullable = false)
    private int tableNr;
    @Column(name = "price", nullable = false)
    private float price;
    @ManyToOne
    @JoinColumn(name ="user_id", nullable = false)
    //@JsonBackReference
    private User user;
    // pentru relatia reducere - comanda
    @ManyToOne
    @JoinColumn(name = "sale_id")
    @JsonBackReference  // ca sa nu afiseze recursiv
      Sale sale;
    //penru produs comanda 1 cmd -> mai multe produse
    @ManyToMany(fetch= FetchType.EAGER)
    @JoinTable(name="ord_product",
            joinColumns = @JoinColumn(name="ord_Id"),
            inverseJoinColumns = @JoinColumn(name="productId"))
    private List<Product> productList;

}

package com.example.martybackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Table(name="user_t")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long userId;
    @Column(name = "firstNameUser",nullable= false)
    private String firstNameUser;
    @Column(name = "secondNameUser",nullable= false)
    private String secondNameUser;
    @Column(name = "role",nullable= false)
    private String role;
    @Column(name = "age",nullable= false)
    private int age;
    @Column(name = "emailUser",nullable= false)
    private String emailUser;
    @Column(name = "passwordUser",nullable= false)
    private String passwordUser;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Ord> ords;
}

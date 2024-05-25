package com.example.martybackend.repository;

import com.example.martybackend.entity.Ord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrdRepository extends JpaRepository<Ord, Long> {
    Optional<Ord> findByTableNr(int tableNumber);


}

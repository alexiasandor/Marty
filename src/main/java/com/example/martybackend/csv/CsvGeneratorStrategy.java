package com.example.martybackend.csv;

import com.example.martybackend.entity.Product;

import java.util.List;

public interface CsvGeneratorStrategy {
    void generateCsvFile(String fileName, List<Product> selectedProducts);
}

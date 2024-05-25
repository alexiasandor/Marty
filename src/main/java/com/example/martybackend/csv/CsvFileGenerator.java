package com.example.martybackend.csv;

import com.example.martybackend.entity.Product;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CsvFileGenerator {

    private final CsvFileGeneratorStrategy csvFileGeneratorStrategy;

    public CsvFileGenerator(@Qualifier("csvFileGeneratorStrategy") CsvFileGeneratorStrategy csvFileGeneratorStrategy) {
        this.csvFileGeneratorStrategy = csvFileGeneratorStrategy;
    }

    public void generateCsvFile(String fileName, List<Product> selectedProducts) {
        csvFileGeneratorStrategy.generateCsvFile(fileName, selectedProducts);
    }
}

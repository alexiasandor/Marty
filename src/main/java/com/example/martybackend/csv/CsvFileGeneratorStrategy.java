package com.example.martybackend.csv;

import com.example.martybackend.entity.Product;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class CsvFileGeneratorStrategy implements CsvGeneratorStrategy {
    @Override
    public void generateCsvFile(String fileName, List<Product> selectedProducts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".csv"))) {

            writer.write("ID,Name,Price,Description\n");


            for (Product product : selectedProducts) {
                writer.write(product.getProductId() + "," + product.getProductName() + "," + product.getPrice() + "," + product.getDescription() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

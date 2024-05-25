package com.example.martybackend.pdfandtxt;

import jakarta.persistence.Entity;
import lombok.*;


@Setter
public class FileGenerator {
    private FileGeneratorStrategy strategy;

    public FileGenerator(FileGeneratorStrategy strategy) {
        this.strategy = strategy;
    }

    public void generateFile(String fileName, String content) {
        strategy.generateFile(fileName, content);
    }
}

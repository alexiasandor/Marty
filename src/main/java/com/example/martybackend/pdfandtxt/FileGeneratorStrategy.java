package com.example.martybackend.pdfandtxt;

public interface FileGeneratorStrategy {
    void generateFile(String fileName, String content);
}

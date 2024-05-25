package com.example.martybackend.pdfandtxt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TxtFileGeneratorStrategy implements FileGeneratorStrategy {
    @Override
    public void generateFile(String fileName, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".txt"))) {

            writer.write("===== Invoice =====\n\n");


            String[] lines = content.split("\n");
            for (String line : lines) {
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

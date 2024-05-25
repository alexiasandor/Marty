package com.example.martybackend.pdfandtxt;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

public class PdfFileGeneratorStrategy implements FileGeneratorStrategy {
    @Override
    public void generateFile(String fileName, String content) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName + ".pdf"));
            document.open();


            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
            Paragraph title = new Paragraph("Invoice", titleFont);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);

            Font regularFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
            Paragraph body = new Paragraph(content, regularFont);
            document.add(body);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

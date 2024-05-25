package com.example.martybackend.pdfandtxt;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class FileGeneratorController {

    @PostMapping(value = "/generate", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> generateFile(@RequestBody FormData formData) {
        try {

            byte[] fileContent;
            if ("pdf".equalsIgnoreCase(formData.getFileType())) {
                fileContent = generatePdf(formData);
            } else if ("txt".equalsIgnoreCase(formData.getFileType())) {
                fileContent = generateTxt(formData);
            } else {
                return ResponseEntity.badRequest().body(null);
            }


            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=invoice." + formData.getFileType())
                    .body(fileContent);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private byte[] generatePdf(FormData formData) {

        String content = String.format("Invoice for: %s %s%nInvoice Value: %s%nAdditional Information: %s",
                formData.getFirstName(), formData.getLastName(), formData.getInvoiceValue(), formData.getAdditionalInfo());
        return content.getBytes();
    }

    private byte[] generateTxt(FormData formData) {

        String content = String.format("Invoice for: %s %s%nInvoice Value: %s%nAdditional Information: %s",
                formData.getFirstName(), formData.getLastName(), formData.getInvoiceValue(), formData.getAdditionalInfo());
        return content.getBytes();
    }
}

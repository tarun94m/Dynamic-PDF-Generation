package com.assignment.pdfgeneration.controller;

import com.assignment.pdfgeneration.model.Order;
import com.assignment.pdfgeneration.service.PdfGenerationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/")
public class PDFGenerationController {
    private PdfGenerationService pdfGenerationService;

    public PDFGenerationController(PdfGenerationService pdfGenerationService) {
        this.pdfGenerationService = pdfGenerationService;
    }

    @PostMapping(path = "/generatePdf")
    public ResponseEntity<?> getDynamicPDF(@RequestBody Order order,
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws IOException, ClassNotFoundException {

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=order.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfGenerationService.getOrGeneratePdf(order, request, response));

    }
}

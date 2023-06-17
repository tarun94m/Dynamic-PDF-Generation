package com.assignment.pdfgeneration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class DynamicPdfGeneration {

    public static void main(String[] args) {
        SpringApplication.run(DynamicPdfGeneration.class, args);
    }

}

package com.assignment.pdfgeneration.service;

import com.assignment.pdfgeneration.model.Order;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

@Service
public class PdfGenerationService {
    private ServletContext servletContext;

    private final TemplateEngine templateEngine;

    private PdfLocalStorageService pdfLocalStorageService;

    public PdfGenerationService(TemplateEngine templateEngine,
                                ServletContext servletContext,
                                PdfLocalStorageService pdfLocalStorageService) {
        this.templateEngine = templateEngine;
        this.servletContext = servletContext;
        this.pdfLocalStorageService = pdfLocalStorageService;
    }

    public byte[] getOrGeneratePdf(Order order,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws IOException, ClassNotFoundException {
        Order storedOrder = pdfLocalStorageService.getOrder(order);
        if (storedOrder != null) {
            return pdfLocalStorageService.getPdf(storedOrder.getOrderId());
        }
        return generateAndStorePdf(order, request, response);
    }

    public byte[] generateAndStorePdf(Order order,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws IOException {

        /* Create HTML using Thymeleaf template Engine */
        order.setOrderId(new Random().nextInt(10000));

        pdfLocalStorageService.storeOrder(order);

        WebContext context = new WebContext(request, response, servletContext);
        context.setVariable("orderEntry", order);
        String orderHtml = templateEngine.process("order", context);

        /* Setup Source and target I/O streams */

        ByteArrayOutputStream target = new ByteArrayOutputStream();
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setBaseUri("http://localhost:8081");
        /* Call convert method */
        HtmlConverter.convertToPdf(orderHtml, target, converterProperties);

        pdfLocalStorageService.storePdf(target, order.getOrderId());

        return target.toByteArray();
    }
}

package com.assignment.pdfgeneration.service;

import com.assignment.pdfgeneration.model.Order;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
public class PdfGenerationServiceTest {

    @Mock
    PdfLocalStorageService pdfLocalStorageService;

    @Mock
    TemplateEngine templateEngine;

    @Mock
    HttpServletRequest request;

    @Mock
    ServletContext servletContext;

    @Mock
    HttpServletResponse response;

    private PdfGenerationService pdfGenerationService;

    @Before
    public void setup(){
        pdfGenerationService=new PdfGenerationService(templateEngine,servletContext,pdfLocalStorageService);
    }

    @Test
    public void testGetOrGeneratePdfWhenOrderExists() throws IOException, ClassNotFoundException {
        Order order = new Order();
        order.setSeller("Dummy");
        order.setOrderId(1);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(byteArrayOutputStream);
        os.writeObject(order);

        Mockito.when(pdfLocalStorageService.getOrder(Mockito.any())).thenReturn(order);
        Mockito.when(pdfLocalStorageService.getPdf(Mockito.anyInt())).thenReturn(byteArrayOutputStream.toByteArray());
        Assert.assertNotNull(pdfGenerationService.getOrGeneratePdf(order, request, response));
    }

    @Test
    public void testGetOrGeneratePdfWhenOrderDoesNotExists() throws IOException, ClassNotFoundException {
        Order order = createOrder();
        Mockito.when(pdfLocalStorageService.getOrder(Mockito.any())).thenReturn(null);
        Mockito.when(templateEngine.process(Mockito.eq("order"),Mockito.anyObject())).thenReturn("aa");
        Assert.assertNotNull(pdfGenerationService.getOrGeneratePdf(order, request, response));
    }

    private Order createOrder() {
        Order order = new Order();
        order.setOrderId(1);
        order.setSeller("Dummy");
        order.setBuyer("Dummy");
        order.setSellerGstin("Dummy");
        order.setBuyerGstin("Dummy");
        order.setBuyerAddress("AA");
        order.setSellerAddress("AA");
        order.setItems(new ArrayList<>());
        return order;
    }
}
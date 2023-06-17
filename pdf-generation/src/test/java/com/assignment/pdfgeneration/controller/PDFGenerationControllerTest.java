package com.assignment.pdfgeneration.controller;

import com.assignment.pdfgeneration.model.Order;
import com.assignment.pdfgeneration.service.PdfGenerationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PDFGenerationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PdfGenerationService pdfGenerationService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new PDFGenerationController(pdfGenerationService)).build();
    }

    @Test
    public void getDynamicPdfAPI() throws Exception {
        Mockito.when(pdfGenerationService.getOrGeneratePdf(Mockito.any(Order.class),
                        Mockito.any(HttpServletRequest.class),
                        Mockito.any(HttpServletResponse.class)))
                .thenReturn("Check".getBytes());
        Order order = createOrder();
        String inputJson = mapToJson(order);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/generatePdf")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getDynamicPdfAPIWithError() throws Exception {
        Mockito.when(pdfGenerationService.getOrGeneratePdf(Mockito.any(Order.class),
                        Mockito.any(HttpServletRequest.class),
                        Mockito.any(HttpServletResponse.class)))
                .thenReturn("Check".getBytes());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/generatePdf")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
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
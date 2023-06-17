package com.assignment.pdfgeneration.service;

import com.assignment.pdfgeneration.model.Item;
import com.assignment.pdfgeneration.model.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class PdfLocalStorageServiceTest {

    private PdfLocalStorageService pdfLocalStorageService;

    @Before
    public void setup() {
        pdfLocalStorageService = new PdfLocalStorageService();
        ReflectionTestUtils.setField(pdfLocalStorageService, "localStorageAddress", "D:\\pdf\\");
        ReflectionTestUtils.setField(pdfLocalStorageService, "orderBeanStorage", "D:\\order\\");
    }

    @Test
    public void testGetAndStoreOrder() throws IOException, ClassNotFoundException {
        Order ord=createOrder(23244);
        ord.setBuyer("New Buyer");
        assertNull(pdfLocalStorageService.getOrder(ord));
        Order order = createOrder(12);
        pdfLocalStorageService.storeOrder(order);
        assertEquals(order, pdfLocalStorageService.getOrder(order));
    }

    @Test
    public void testGetAndStorePdf() throws IOException {
        Order order = createOrder(13);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(order);
        out.flush();
        pdfLocalStorageService.storePdf(bos, 13);
        assertNotNull(pdfLocalStorageService.getPdf(13));
    }

    @Test(expected = NoSuchFileException.class)
    public void testGetPdfWhenFileDoesNotExist() throws IOException {
        pdfLocalStorageService.getPdf(123456789);
    }

    private Order createOrder(int id) {
        Order order = new Order();
        order.setOrderId(id);
        order.setSeller("Dummy");
        order.setBuyer("Dummy");
        order.setSellerGstin("Dummy");
        order.setBuyerGstin("Dummy");
        order.setBuyerAddress("AA");
        order.setSellerAddress("AA");
        order.setItems(new ArrayList<>());

        Item item = new Item();
        item.setAmount(100.00);
        item.setRate(123.45);
        item.setQuantity("20 nos");
        item.setName("Brush");
        ArrayList<Item> i = new ArrayList<>();
        i.add(item);
        order.setItems(i);
        return order;
    }

}
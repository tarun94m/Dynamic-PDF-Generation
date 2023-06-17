package com.assignment.pdfgeneration.service;

import com.assignment.pdfgeneration.model.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PdfLocalStorageService {
    @Value("${pdf.local.storage.location}")
    private String localStorageAddress;

    @Value("${pdf.order.bean.location}")
    private String orderBeanStorage;

    public void storePdf(ByteArrayOutputStream bytes, int orderId) throws IOException {
        String filename = orderId + ".pdf";
        FileOutputStream output = new FileOutputStream(localStorageAddress + filename);
        output.write(bytes.toByteArray());
        output.close();
    }

    public byte[] getPdf(int orderId) throws IOException {
        String filename = orderId + ".pdf";
        Path pdfPath = Paths.get(localStorageAddress + filename);
        return Files.readAllBytes(pdfPath);
    }

    public void storeOrder(Order order) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(orderBeanStorage + order.getOrderId() + ".ser");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(order);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public Order getOrder(Order order) throws IOException, ClassNotFoundException {
        File dir = new File(orderBeanStorage);
        File[] directoryListing = dir.listFiles();
        for (File f : directoryListing) {
            FileInputStream fileInputStream = new FileInputStream(f);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Order storedOrder = (Order) objectInputStream.readObject();
            objectInputStream.close();
            if (order.equals(storedOrder)) {
                return storedOrder;
            }
        }
        return null;
    }
}

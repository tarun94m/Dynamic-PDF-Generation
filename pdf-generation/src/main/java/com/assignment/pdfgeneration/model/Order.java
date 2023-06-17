package com.assignment.pdfgeneration.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Data
public class Order implements Serializable {
    private Integer orderId;
    private String seller;
    private String sellerGstin;
    private String sellerAddress;
    private String buyer;
    private String buyerGstin;
    private String buyerAddress;
    private List<Item> items;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return seller.equals(order.seller) && sellerGstin.equals(order.sellerGstin) && sellerAddress.equals(order.sellerAddress) && buyer.equals(order.buyer) && buyerGstin.equals(order.buyerGstin) && buyerAddress.equals(order.buyerAddress) && items.equals(order.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seller, sellerGstin, sellerAddress, buyer, buyerGstin, buyerAddress, items);
    }
}

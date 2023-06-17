package com.assignment.pdfgeneration.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Item implements Serializable {
    private String name;
    private String quantity;
    private Double rate;
    private Double amount;
}

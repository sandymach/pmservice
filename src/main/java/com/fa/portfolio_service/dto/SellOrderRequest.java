package com.fa.portfolio_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SellOrderRequest {
    private String symbol;
    private Long quantity;
    private BigDecimal price;
}

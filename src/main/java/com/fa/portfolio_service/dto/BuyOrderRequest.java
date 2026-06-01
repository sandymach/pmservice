package com.fa.portfolio_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BuyOrderRequest {
    private String symbol;
    private Long quantity;
    private BigDecimal price;
}

package com.fa.portfolio_service.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class PortfolioResponse {
    private Long clientId;
    private BigDecimal totalValue;
    private BigDecimal cashBalance;
    private List<HoldingDto> holdings;

    @Data
    @Builder
    public static class HoldingDto {
        private String symbol;
        private Long quantity;
        private BigDecimal averagePrice;
    }
}

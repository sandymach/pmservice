package com.fa.portfolio_service.mapper;



import com.fa.portfolio_service.dto.PortfolioResponse;
import com.fa.portfolio_service.entity.Holding;
import com.fa.portfolio_service.entity.Portfolio;

import java.util.List;
import java.util.stream.Collectors;

public class PortfolioMapper {

    public static PortfolioResponse toResponse(Portfolio portfolio,
                                               List<Holding> holdings) {

        return PortfolioResponse.builder()
                .clientId(portfolio.getClientId())
                .totalValue(portfolio.getTotalValue())
                .holdings(
                        holdings.stream()
                                .map(h -> PortfolioResponse.HoldingDto.builder()
                                        .symbol(h.getSymbol())
                                        .quantity(h.getQuantity())
                                        .averagePrice(h.getAveragePrice())
                                        .build())
                                .collect(Collectors.toList())
                )
                .build();
    }
}

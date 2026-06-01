package com.fa.portfolio_service.service;

import java.math.BigDecimal;

public interface HoldingService {

    void updateMarketPrice(Long portfolioId,
                           String symbol,
                           BigDecimal marketPrice);
}

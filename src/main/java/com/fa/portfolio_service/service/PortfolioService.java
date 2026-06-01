package com.fa.portfolio_service.service;

import com.fa.portfolio_service.dto.BuyOrderRequest;
import com.fa.portfolio_service.entity.Portfolio;

public interface PortfolioService {
    Portfolio createPortfolio(Long clientId);

    void processBuyOrder(Long clientId, BuyOrderRequest request);
}

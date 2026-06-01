package com.fa.portfolio_service.service;

import com.fa.portfolio_service.dto.BuyOrderRequest;
import com.fa.portfolio_service.entity.Portfolio;
import com.fa.portfolio_service.kafka.producer.TradeRequestProducer;
import com.fa.portfolio_service.repository.PortfolioRepository;
import com.fin.commo_event_lib.events.TradeRequestEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final TradeRequestProducer producer;

    @Override
    public Portfolio createPortfolio(Long clientId) {

        Portfolio portfolio = Portfolio.builder()
                .clientId(clientId)
                .totalValue(java.math.BigDecimal.ZERO)
                .build();

        return portfolioRepository.save(portfolio);
    }

    @Override
    public void processBuyOrder(Long clientId, BuyOrderRequest request) {

        Portfolio portfolio = portfolioRepository.findByClientId(clientId)
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));

        TradeRequestEvent event = TradeRequestEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .portfolioId(portfolio.getPortfolioId())
                .symbol(request.getSymbol())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .side("BUY")
                .build();

        producer.send(event);
    }
}

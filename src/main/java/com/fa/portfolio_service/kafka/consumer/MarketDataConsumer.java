package com.fa.portfolio_service.kafka.consumer;

import com.fa.portfolio_service.service.HoldingService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MarketDataConsumer {

    private final HoldingService holdingService;

    @KafkaListener(topics = "market-prices", groupId = "portfolio-group")
    public void consume(com.fin.commo_event_lib.events.MarketPriceEvent event) {

        // In real system → loop all portfolios holding this symbol
        // simplified example:
        holdingService.updateMarketPrice(
                null,
                event.getSymbol(),
                event.getPrice()
        );
    }
}
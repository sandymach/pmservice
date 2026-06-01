package com.fa.portfolio_service.kafka.consumer;

import com.fa.portfolio_service.service.HoldingService;
import com.fa.portfolio_service.service.HoldingServiceImpl;
import com.fin.commo_event_lib.events.TradeExecutedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TradeExecutedConsumer {

    private final HoldingServiceImpl holdingService;

    @KafkaListener(topics = "trade-executed", groupId = "portfolio-group")
    public void consume(TradeExecutedEvent event) {

        holdingService.updateHolding( event.getPortfolioId(),
                event.getSymbol(),
                event.getQuantity(),
                event.getExecutionPrice());
    }
}

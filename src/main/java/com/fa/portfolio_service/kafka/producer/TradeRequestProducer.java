package com.fa.portfolio_service.kafka.producer;

import com.fin.commo_event_lib.events.TradeRequestEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TradeRequestProducer {

    private final KafkaTemplate<String,TradeRequestEvent> kafkaTemplate;

    public void send(TradeRequestEvent event) {
       kafkaTemplate.send("trade-requests", event.getEventId(), event);
   }
}

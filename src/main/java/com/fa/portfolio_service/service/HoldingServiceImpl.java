package com.fa.portfolio_service.service;

import com.fa.portfolio_service.entity.Holding;
import com.fa.portfolio_service.repository.HoldingRepository;
import com.fin.commo_event_lib.events.TradeExecutedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class HoldingServiceImpl implements  HoldingService {
    private final HoldingRepository holdingRepository;
    private final PnLService pnlService;

    @Override
    public void updateMarketPrice(Long portfolioId,
                                  String symbol,
                                  BigDecimal marketPrice) {

        Holding holding = holdingRepository
                .findByPortfolioIdAndSymbol(portfolioId, symbol)
                .orElse(null);

        if (holding == null) return;

        BigDecimal pnl = pnlService.calculatePnL(holding, marketPrice);

        holding.setPnl(pnl);

        holdingRepository.save(holding);
    }

    public void processTrade(TradeExecutedEvent event) {

        Holding h = holdingRepository.findByPortfolioIdAndSymbol(
                event.getPortfolioId(),
                event.getSymbol()
        ).orElse(Holding.builder()
                .id(event.getPortfolioId())
                .symbol(event.getSymbol())
                .quantity(0L)
                .averagePrice(BigDecimal.ZERO)
                .pnl(BigDecimal.ZERO)
                .build());

        if ("BUY".equals(event.getSide())) {
            buy(h, event.getQuantity(), event.getExecutionPrice());
        } else {
            sell(h, event.getQuantity());
        }

      pnlService.calculatePnL(h,event.getExecutionPrice());
        holdingRepository.save(h);
    }

    private void buy(Holding h, Long qty, BigDecimal price) {

        long newQty = h.getQuantity() + qty;

        BigDecimal newAvg =
                h.getAveragePrice()
                        .multiply(BigDecimal.valueOf(h.getQuantity()))
                        .add(price.multiply(BigDecimal.valueOf(qty)))
                        .divide(BigDecimal.valueOf(newQty), 4, BigDecimal.ROUND_HALF_UP);

        h.setQuantity(newQty);
        h.setAveragePrice(newAvg);
    }

    private void sell(Holding h, Long qty) {
        h.setQuantity(h.getQuantity() - qty);
    }


    public void updateHolding(Long portfolioId,
                              String symbol,
                              Long quantity,
                              BigDecimal price) {

        Holding holding = holdingRepository
                .findByPortfolioIdAndSymbol(portfolioId, symbol)
                .orElse(Holding.builder()
                        .id(portfolioId)
                        .symbol(symbol)
                        .quantity(0L)
                        .averagePrice(BigDecimal.ZERO)
                        .build());

        long newQty = holding.getQuantity() + quantity;

        BigDecimal newAvgPrice = price;

        holding.setQuantity(newQty);
        holding.setAveragePrice(newAvgPrice);

        holdingRepository.save(holding);
    }

}

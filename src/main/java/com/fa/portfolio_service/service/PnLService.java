package com.fa.portfolio_service.service;

import com.fa.portfolio_service.entity.Holding;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class PnLService {
    //PnL = (marketPrice - avgPrice) × quantity
    public BigDecimal calculatePnL(Holding holding, BigDecimal marketPrice) {

        if (holding.getQuantity() == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal diff = marketPrice.subtract(holding.getAveragePrice());

        return diff.multiply(BigDecimal.valueOf(holding.getQuantity()))
                .setScale(4, RoundingMode.HALF_UP);
    }

    public BigDecimal calculatePortfolioPnL(List<Holding> holdings) {

        return holdings.stream()
                .map(Holding::getPnl)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

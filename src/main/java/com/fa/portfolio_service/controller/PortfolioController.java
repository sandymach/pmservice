package com.fa.portfolio_service.controller;

import com.fa.portfolio_service.dto.BuyOrderRequest;
import com.fa.portfolio_service.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @PostMapping("/{clientId}/buy")
    public String buy(@PathVariable Long clientId,
                      @RequestBody BuyOrderRequest request) {

        portfolioService.processBuyOrder(clientId, request);
        return "BUY order sent to Trade Service";
    }

    @PostMapping("/{clientId}/create")
    public String create(@PathVariable Long clientId) {
        portfolioService.createPortfolio(clientId);
        return "Portfolio created";
    }

}

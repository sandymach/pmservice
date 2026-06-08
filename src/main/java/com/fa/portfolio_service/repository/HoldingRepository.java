package com.fa.portfolio_service.repository;

import com.fa.portfolio_service.entity.Holding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HoldingRepository  extends JpaRepository<Holding, Long> {
    List<Holding> findBySymbol(String symbol);
    List<Holding> findByPortfolioPortfolioId(Long portfolioId);
    Optional<Holding> findByPortfolioPortfolioIdAndSymbol(Long portfolioId, String symbol);
}

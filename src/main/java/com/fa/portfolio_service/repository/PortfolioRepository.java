package com.fa.portfolio_service.repository;

import com.fa.portfolio_service.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    Optional<Portfolio> findByClientId(Long clientId);
}

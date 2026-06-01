package com.fa.portfolio_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "portfolio",
        indexes = {
                @Index(name = "idx_client_id", columnList = "clientId")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long portfolioId;

    /**
     * Business identifier for the customer.
     * One client can have only one or multiple portfolios depending on business rules.
     */
    @Column(nullable = false)
    private Long clientId;

    /**
     * Total current value of the portfolio.(Represents market value of holdings + cash)
     * Updated after every trade execution or market valuation event.
     */
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal totalValue;

    /**
     * Cash available in the portfolio for new trades.
     */
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal cashBalance;

    /**
     * Currency of portfolio (useful for multi-country financial systems)
     * Example: USD, EUR, SEK
     */
    @Column(length = 10, nullable = false)
    private String currency;

    /**
     * Status of portfolio:
     * ACTIVE, SUSPENDED, CLOSED
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PortfolioStatus status;

    /**
     * Timestamp when portfolio was created.
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp when portfolio was last updated.
     */
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Profit and Loss tracking
     */
    @Column(precision = 19, scale = 4)
    private BigDecimal pnl;

    /**
     * Portfolio type:
     * RETAIL, HNI, INSTITUTIONAL, RETIREMENT
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private PortfolioType type;

    /**
     * Risk profile of client
     * LOW, MEDIUM, HIGH
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RiskProfile riskProfile;

    /**
     * One Portfolio -> Many Holdings
     */
    @OneToMany(
            mappedBy = "portfolio",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Holding> holdings = new ArrayList<>();

    /**
     * Optimistic locking field.
     * Prevents concurrent update issues when multiple trades update portfolio simultaneously.
     */
    @Version
    private Long version;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        if (this.cashBalance == null) {
            this.cashBalance = BigDecimal.ZERO;
        }

        if (this.totalValue == null) {
            this.totalValue = BigDecimal.ZERO;
        }

        if (this.status == null) {
            this.status = PortfolioStatus.ACTIVE;
        }
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

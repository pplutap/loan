package com.pawel.loan.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
@ConfigurationProperties("loan")
@Getter
@Setter
public class LoanProperties {

    private BigDecimal maxAmount;
    private BigDecimal minAmount;
    private Integer maxTerm;
    private Integer minTerm;
    private Integer extendTerm;
    private BigDecimal costOfIssue;
}

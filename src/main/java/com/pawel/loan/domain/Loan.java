package com.pawel.loan.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class Loan {

    public Loan(BigDecimal amount, LocalDate startDate, LocalDate endDate, BigDecimal cost) {
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cost = cost;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Positive
    @Column(precision = 10, scale = 4)
    private BigDecimal amount;

    @FutureOrPresent
    @Column
    private LocalDate startDate;

    @Future
    @Column
    private LocalDate endDate;

    @Positive
    @Column(precision = 10, scale = 4)
    private BigDecimal cost;
}

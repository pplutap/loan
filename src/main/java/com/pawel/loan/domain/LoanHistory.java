package com.pawel.loan.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class LoanHistory {

    public LoanHistory(Long loanId, LocalDate startDate, LocalDate previousEndDate, LocalDate newEndDate) {
        this.loanId = loanId;
        this.startDate = startDate;
        this.previousEndDate = previousEndDate;
        this.newEndDate = newEndDate;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Long loanId;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate previousEndDate;

    @Column
    private LocalDate newEndDate;
}

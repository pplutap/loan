package com.pawel.loan.service;

import com.pawel.loan.config.LoanProperties;
import com.pawel.loan.domain.Loan;
import com.pawel.loan.domain.LoanHistory;
import com.pawel.loan.dto.LoanInputDto;
import com.pawel.loan.exception.LoanNotFoundException;
import com.pawel.loan.repository.LoanHistoryRepository;
import com.pawel.loan.repository.LoanRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoanServiceTest {

    @Mock
    private LoanProperties loanProperties;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private LoanHistoryRepository loanHistoryRepository;

    @InjectMocks
    private LoanService loanService;

    @Test
    public void testSaveLoan() {
        //Given
        LoanInputDto inputDto = new LoanInputDto(BigDecimal.valueOf(1500), 15);
        when(loanProperties.getCostOfIssue()).thenReturn(BigDecimal.ONE);

        //When
        loanService.saveLoan(inputDto);

        //Then
        verify(loanRepository, times(1)).save(any(Loan.class));
    }

    @Test
    public void testExtendLoan() throws LoanNotFoundException {
        //Given
        LocalDate date = LocalDate.of(2018, 1, 1);
        Loan loan = new Loan(BigDecimal.TEN, date, date.plusDays(4), BigDecimal.ONE);
        when(loanRepository.findById(any())).thenReturn(Optional.of(loan));
        when(loanProperties.getExtendTerm()).thenReturn(10);

        //When
        LocalDate result = loanService.extendLoan(10L);

        //Then
        Assert.assertEquals(LocalDate.of(2018, 1, 15), result);
        verify(loanHistoryRepository, times(1)).save(any(LoanHistory.class));
        verify(loanRepository, times(1)).updateStatus(10L, result);
    }

    @Test(expected = LoanNotFoundException.class)
    public void testExtendLoanNotExisting() throws LoanNotFoundException {
        //Given
        when(loanRepository.findById(any())).thenReturn(Optional.empty());

        //When
        loanService.extendLoan(10L);
    }
}
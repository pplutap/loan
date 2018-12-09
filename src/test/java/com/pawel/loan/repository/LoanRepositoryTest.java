package com.pawel.loan.repository;

import com.pawel.loan.domain.Loan;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoanRepositoryTest {

    @Autowired
    private LoanRepository loanRepository;

    @Test
    public void testSaveLoan() {
        //Given
        Loan loan = new Loan(BigDecimal.TEN, LocalDate.now(), LocalDate.now().plusDays(3), BigDecimal.ONE);
        //When
        Loan saved = loanRepository.save(loan);
        //Then
        Assert.assertNotEquals(0L, (long) saved.getId());
    }

    @Test
    public void testUpdateStatus() {
        //Given
        LocalDate localDate = LocalDate.now();
        Loan loan = new Loan(BigDecimal.TEN, localDate, localDate.plusDays(3), BigDecimal.ONE);
        Loan saved = loanRepository.save(loan);

        //When
        loanRepository.updateStatus(saved.getId(), localDate.plusDays(20));
        Optional<Loan> optional = loanRepository.findById(saved.getId());

        //Then
        Assert.assertTrue(optional.isPresent());
        Assert.assertEquals(localDate.plusDays(20), optional.get().getEndDate());
    }
}
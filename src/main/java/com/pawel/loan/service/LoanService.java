package com.pawel.loan.service;

import com.pawel.loan.config.LoanProperties;
import com.pawel.loan.domain.Loan;
import com.pawel.loan.domain.LoanHistory;
import com.pawel.loan.dto.LoanInputDto;
import com.pawel.loan.exception.LoanNotFoundException;
import com.pawel.loan.repository.LoanHistoryRepository;
import com.pawel.loan.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class LoanService {

    @Autowired
    private LoanProperties loanProperties;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LoanHistoryRepository loanHistoryRepository;

    public void saveLoan(LoanInputDto dto) {
        loanRepository.save(new Loan(dto.getAmount(), LocalDate.now(), LocalDate.now().plusDays(10),
                dto.getAmount().multiply(loanProperties.getCostOfIssue())));
    }

    @Transactional
    public LocalDate extendLoan(Long id) throws LoanNotFoundException {
        Optional<Loan> optionalLoan = loanRepository.findById(id);
        Loan loan = optionalLoan.orElseThrow(LoanNotFoundException::new);
        LocalDate newEndDate = loan.getEndDate().plusDays(loanProperties.getExtendTerm());
        loanHistoryRepository.save(new LoanHistory(loan.getId(), loan.getStartDate(), loan.getEndDate(), newEndDate));
        loanRepository.updateStatus(id, newEndDate);
        return newEndDate;
    }


}

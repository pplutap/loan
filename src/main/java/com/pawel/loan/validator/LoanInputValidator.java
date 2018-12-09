package com.pawel.loan.validator;

import com.pawel.loan.config.LoanProperties;
import com.pawel.loan.dto.LoanInputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalTime;

@Component
public class LoanInputValidator implements Validator {

    @Autowired
    private LoanProperties loanProperties;

    @Override
    public boolean supports(Class<?> clazz) {
        return LoanInputDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LoanInputDto loan = (LoanInputDto) target;
        if (loan.getAmount().compareTo(loanProperties.getMinAmount()) < 0) {
            errors.rejectValue("amount", "too small");
        }
        if (loan.getAmount().compareTo(loanProperties.getMaxAmount()) > 0) {
            errors.rejectValue("amount", "too big");
        }
        if (loan.getTerm() < loanProperties.getMinTerm()) {
            errors.rejectValue("term", "too short");
        }
        if (loan.getTerm() > loanProperties.getMaxTerm()) {
            errors.rejectValue("term", "too long");
        }
        if (loan.getAmount().equals(loanProperties.getMaxAmount()) && isRequestBetweenDangerTime()) {
            errors.reject("wrong time with big amount");
        }
    }

    private boolean isRequestBetweenDangerTime() {
        LocalTime now = LocalTime.now();
        return now.isAfter(LocalTime.MIDNIGHT) && now.isBefore(LocalTime.of(6, 0));
    }
}

package com.pawel.loan.controller;

import com.pawel.loan.config.LoanProperties;
import com.pawel.loan.dto.LoanInputDto;
import com.pawel.loan.dto.LoanResultDto;
import com.pawel.loan.exception.LoanNotFoundException;
import com.pawel.loan.service.LoanService;
import com.pawel.loan.validator.LoanInputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("loan")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private LoanInputValidator validator;

    @PostMapping("apply_for_loan")
    public LoanResultDto applyForLoan(@RequestBody LoanInputDto loanInputDto, BindingResult result) {
        validator.validate(loanInputDto, result);
        if (result.hasErrors()) {
            return new LoanResultDto(LoanResultDto.FAIL, result.getAllErrors().toString());
        }
        loanService.saveLoan(loanInputDto);
        return new LoanResultDto(LoanResultDto.OK, LoanResultDto.CREATED);
    }

    @PostMapping("extend_loan")
    public String extendLoan(@RequestParam Long loanId) throws LoanNotFoundException {
        LocalDate newEndDate = loanService.extendLoan(loanId);
        return "New end date is " + newEndDate.format(DateTimeFormatter.ISO_DATE);
    }
}

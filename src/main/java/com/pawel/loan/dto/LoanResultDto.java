package com.pawel.loan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanResultDto {

    public static final String OK = "OK";
    public static final String FAIL = "FAIL";
    public static final String CREATED = "Loan created";

    private String status;
    private String message;
}

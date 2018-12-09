package com.pawel.loan.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
public class LoanInputDto {

    @NotNull
    private BigDecimal amount;

    @NotNull
    private Integer term;
}

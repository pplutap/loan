package com.pawel.loan.controller;

import com.google.gson.Gson;
import com.pawel.loan.dto.LoanInputDto;
import com.pawel.loan.service.LoanService;
import com.pawel.loan.validator.LoanInputValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LoanController.class)
public class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanService loanService;

    @MockBean
    private LoanInputValidator validator;

    @Test
    public void testApplyForLoan() throws Exception {
        //Given
        LoanInputDto loanDto = new LoanInputDto(BigDecimal.valueOf(400), 15);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(loanDto);

        //When & Then
        mockMvc.perform(post("/loan/apply_for_loan")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
        verify(loanService, times(1)).saveLoan(any());
    }

    @Test
    public void testExtendLoan() throws Exception {
        //Given
        LocalDate endDate = LocalDate.now().plusDays(15);
        String expected = "New end date is " + endDate.format(DateTimeFormatter.ISO_DATE);
        when(loanService.extendLoan(anyLong())).thenReturn(endDate);

        //When & Then
        mockMvc.perform(post("/loan/extend_loan")
                .param("loanId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(expected)));
    }
}
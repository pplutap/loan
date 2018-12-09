package com.pawel.loan.integration;

import com.pawel.loan.LoanApplication;
import com.pawel.loan.domain.Loan;
import com.pawel.loan.dto.LoanInputDto;
import com.pawel.loan.dto.LoanResultDto;
import com.pawel.loan.repository.LoanRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LoanApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class LoalControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private LoanRepository loanRepository;

    private TestRestTemplate restTemplate = new TestRestTemplate();
    private HttpHeaders headers = new HttpHeaders();

    @Test
    public void applyForLoanTest() {

        LoanInputDto loanInputDto = new LoanInputDto(BigDecimal.valueOf(500), 15);

        HttpEntity<LoanInputDto> entity = new HttpEntity<>(loanInputDto, headers);

        ResponseEntity<LoanResultDto> response = restTemplate.exchange(createURLWithPort("/loan/apply_for_loan"),
                HttpMethod.POST, entity, LoanResultDto.class);

        LoanResultDto actual = response.getBody();

        Assert.assertEquals("OK", actual.getStatus());
        Assert.assertEquals("Loan created", actual.getMessage());

    }

    @Test
    public void extendLoanTest() {
        LocalDate now = LocalDate.now();
        Loan loan = new Loan(BigDecimal.valueOf(700), now, now.plusDays(10), BigDecimal.ONE);
        Loan saved = loanRepository.save(loan);
        String expected = "New end date is " + now.plusDays(25).format(DateTimeFormatter.ISO_DATE);

        ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort("/loan/extend_loan?loanId=" + saved.getId()),
                HttpMethod.POST, String.class);

        String result = response.getBody();
        Assert.assertEquals(expected, result);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}

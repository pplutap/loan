package com.pawel.loan.repository;

import com.pawel.loan.domain.Loan;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Transactional
@Repository
public interface LoanRepository extends CrudRepository<Loan, Long> {

    @Modifying
    @Query("update Loan set endDate = :endDate where id = :id")
    void updateStatus(@Param("id") Long id, @Param("endDate") LocalDate endDate);

}

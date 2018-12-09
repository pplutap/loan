package com.pawel.loan.repository;

import com.pawel.loan.domain.LoanHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface LoanHistoryRepository extends CrudRepository<LoanHistory, Long> {

}

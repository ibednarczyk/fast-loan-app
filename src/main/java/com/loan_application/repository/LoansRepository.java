package com.loan_application.repository;

import com.loan_application.domain.loan.Loan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface LoansRepository extends CrudRepository<Loan, Long> {

    @Override
    List<Loan> findAll();

    @Override
    Optional<Loan> findById(Long aLong);

    @Override
    <S extends Loan> S save(S entity);

    Optional<Loan> findByApplicationId(Long appId);
}


package com.loan_application.repository;

import com.loan_application.domain.application.Application;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ApplicationsRepository extends CrudRepository<Application, Long> {

    @Override
    List<Application> findAll();

    @Override
    Optional<Application> findById(Long aLong);

    @Override
    <S extends Application> S save(S entity);
}

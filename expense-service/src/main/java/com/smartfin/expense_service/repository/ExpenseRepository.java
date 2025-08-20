package com.smartfin.expense_service.repository;

import com.smartfin.expense_service.model.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends MongoRepository<Expense, String> {
    List<Expense> findByEmail(String email);

    int deleteByUserName(String userId);

    List<Expense> findByUserName(String userName);

    long countByEmail(String email);
}

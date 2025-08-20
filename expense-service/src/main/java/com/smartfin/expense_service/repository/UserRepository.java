package com.smartfin.expense_service.repository;

import com.smartfin.expense_service.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    Optional<User> findByEmail(String email);
}

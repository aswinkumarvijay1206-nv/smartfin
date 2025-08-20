package com.smartfin.expense_service.controller;

import com.smartfin.expense_service.model.Expense;
import com.smartfin.expense_service.model.User;
import com.smartfin.expense_service.repository.ExpenseRepository;
import com.smartfin.expense_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;

    @Autowired
    public UserController(UserRepository userRepository, ExpenseRepository expenseRepository) {
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
    }

    @GetMapping
    public List<User> getusers() {
        return userRepository.findAll();
    }

    @GetMapping("/profile")
    public ResponseEntity<Expense> getProfile(Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        long userExpenseCount = expenseRepository.countByEmail(email);

        Expense expense = new Expense();
        expense.setUserName(user.getUsername());
        expense.setEmail(email);
        expense.setCount(userExpenseCount);

        return ResponseEntity.ok(expense);
    }


}

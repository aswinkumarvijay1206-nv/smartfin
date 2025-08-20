package com.smartfin.expense_service.controller;

import com.smartfin.expense_service.model.Expense;
import com.smartfin.expense_service.model.User;
import com.smartfin.expense_service.repository.ExpenseRepository;
import com.smartfin.expense_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    @Autowired
    public ExpenseController(ExpenseRepository expenseRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense, Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User Not Found"));
        expense.setUserName(user.getUsername());
        expense.setEmail(user.getEmail());
        return ResponseEntity.ok(expenseRepository.save(expense));
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses(Principal principal) {
        String email = principal.getName();
        List<Expense> expenses =  expenseRepository.findByEmail(email);
        return ResponseEntity.ok(expenses);
    }
    
    @GetMapping("/{id}")
    public Optional<Expense> getExpenseById(@PathVariable String id){
        return expenseRepository.findById(id);
    }

    @GetMapping("/user/{userName}")
    public List<Expense> getExpenseByUserName(@PathVariable String userName){
        return expenseRepository.findByUserName(userName);
    }

    @GetMapping("/user")
    public List<Expense> getExpenseByUserIdReq(@RequestParam String userName){
        return expenseRepository.findByUserName(userName);
    }

    @DeleteMapping("/user/{userName}")
    public String deleteExpenseByUserName(@PathVariable String userName) {
        return expenseRepository.deleteByUserName(userName) > 0 ? "Success" : "Failed";
    }
}


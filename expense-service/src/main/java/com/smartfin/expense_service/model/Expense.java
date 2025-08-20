package com.smartfin.expense_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "expenses")
public class Expense {
    @Id
    private String id;
    private String userName;
    private double amount;
    private String category;
    private String date;
    private String email;
    private long count;

//    public Expense() {
//
//    }
//
//    public Expense(String userName, String email, long count) {
//        this.userName = userName;
//        this.email = email;
//        this.count = count;
//    }

    // Getters and Setters (or use Lombok @Data)
}

package com.smartfin.expense_report.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ReportRequestDTO {
    private String reportType;
    private Map<String, String> parameters;
    private String sheetType;
    private List<String> headers;

    // Getters and Setters
}



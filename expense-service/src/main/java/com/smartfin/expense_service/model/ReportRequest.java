package com.smartfin.expense_service.model;

import lombok.Data;

import java.util.Map;

@Data
public class ReportRequest {

    private String reportType; // "EXPENSE", "SUMMARY", etc.
    private String sheetType;  // "EXCEL", "TXT"
    private Map<String, String> parameters; // flexible input map
}

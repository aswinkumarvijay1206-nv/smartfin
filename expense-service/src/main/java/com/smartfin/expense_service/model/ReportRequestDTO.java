package com.smartfin.expense_service.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ReportRequestDTO {
    private String reportType; // e.g., "EXPENSE"
    private Map<String, String> parameters; // e.g., fromDate, toDate, email
    private List<String> headers; // column headers like ["Date", "Amount", "Category"]
    private String sheetType; // "EXCEL" or "TXT"

}

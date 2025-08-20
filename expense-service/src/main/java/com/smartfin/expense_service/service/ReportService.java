package com.smartfin.expense_service.service;

import com.smartfin.expense_service.model.ReportRequest;

import java.util.List;

public interface ReportService {
    byte[] generateReport(ReportRequest request,List<String> headers);
}

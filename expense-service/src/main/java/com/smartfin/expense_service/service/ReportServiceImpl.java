package com.smartfin.expense_service.service;

import com.smartfin.expense_service.generator.ExpenseReportGenerator;
import com.smartfin.expense_service.model.ReportRequest;
import com.smartfin.expense_service.model.ReportRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    private final ExpenseReportGenerator expenseReportGenerator;

    @Autowired
    public ReportServiceImpl(ExpenseReportGenerator expenseReportGenerator) {
        this.expenseReportGenerator = expenseReportGenerator;
    }

    @Override
    public byte[] generateReport(ReportRequest request, List<String> headers) {
        ReportRequestDTO req = new ReportRequestDTO();
        req.setReportType(request.getReportType());
        req.setParameters(request.getParameters());
        req.setSheetType(request.getSheetType());
        req.setHeaders(headers);
        return expenseReportGenerator.generateReport(req);
    }

}

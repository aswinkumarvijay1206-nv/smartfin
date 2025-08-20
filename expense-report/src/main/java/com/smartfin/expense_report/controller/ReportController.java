package com.smartfin.expense_report.controller;

import com.smartfin.expense_report.dto.ReportRequestDTO;
import com.smartfin.expense_report.service.ReportGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private ReportGeneratorService reportGeneratorService;

    @PostMapping("/generate")
    public ResponseEntity<byte[]> generateReport(@RequestBody ReportRequestDTO request) {
        try {
            byte[] fileBytes = reportGeneratorService.generateReport(request);
            String extension = request.getSheetType().equalsIgnoreCase("TXT") ? "txt" : "xlsx";
            String filename = request.getReportType() + "_Report." + extension;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(ContentDisposition.attachment().filename(filename).build());
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error generating report: " + e.getMessage()).getBytes());
        }
    }

}


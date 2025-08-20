package com.smartfin.expense_service.controller;

import com.smartfin.expense_service.model.ReportRequest;
import com.smartfin.expense_service.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/download")
    public ResponseEntity<byte[]> generateReport(@RequestBody ReportRequest request) {
        List<String> header = List.of("Date", "Category", "Amount");
//        request.setParameters(new Map.entry('email', email));
        byte[] fileBytes = reportService.generateReport(request, header);

        String extension = request.getSheetType().equalsIgnoreCase("TXT") ? "txt" : "xlsx";
        String filename = request.getReportType() + "_Report." + extension;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment().filename(filename).build());
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
    }
}

package com.smartfin.expense_service.generator;

import com.smartfin.expense_service.model.ReportRequestDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ExpenseReportGenerator {

    @Value("${report.service.url}")
    private String reportServiceUrl;  // e.g., http://localhost:9090/api/reports/generate

    private final RestTemplate restTemplate = new RestTemplate();

    public byte[] generateReport(ReportRequestDTO reportRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ReportRequestDTO> requestEntity = new HttpEntity<>(reportRequest, headers);

        ResponseEntity<byte[]> response = restTemplate.exchange(
                reportServiceUrl,
                HttpMethod.POST,
                requestEntity,
                byte[].class
        );

        return response.getBody();
    }
}

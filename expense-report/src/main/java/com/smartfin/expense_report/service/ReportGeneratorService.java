package com.smartfin.expense_report.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartfin.expense_report.dto.ReportRequestDTO;
import com.smartfin.expense_report.generator.ExcelGenerator;
import com.smartfin.expense_report.generator.TxtGenerator;
import com.smartfin.expense_report.util.QueryLoader;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

@Service
public class ReportGeneratorService {

    @Autowired
    private QueryLoader queryLoader;

    @Autowired
    private MongoTemplate mongoTemplate;

    public byte[] generateReport(ReportRequestDTO request) {
        String rawQuery = queryLoader.getQuery(request.getReportType());
        if (rawQuery == null) {
            throw new IllegalArgumentException("Query not found for report type: " + request.getReportType());
        }

        // Validate collection exists
        if (!mongoTemplate.collectionExists(request.getReportType())) {
            throw new IllegalArgumentException("Collection not found in MongoDB: " + request.getReportType());
        }

        String filledQuery = applyParameters(rawQuery, request.getParameters());

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<List<Map<String, Object>>> future = executor.submit(() -> {
            BasicQuery query = new BasicQuery(Document.parse(filledQuery));
            List<Document> docs = mongoTemplate.find(query, Document.class, request.getReportType());

            List<Map<String, Object>> result = new ArrayList<>();
            for (Document doc : docs) {
                result.add(new ObjectMapper().readValue(doc.toJson(), Map.class));
            }
            return result;
        });

        try {
            List<Map<String, Object>> data = future.get(10, TimeUnit.SECONDS);
            validateHeaders(request.getHeaders(), data);
            return "TXT".equalsIgnoreCase(request.getSheetType())
                    ? TxtGenerator.generateTxt(data, request.getHeaders())
                    : ExcelGenerator.generateExcel(data, request.getHeaders());
        } catch (Exception e) {
            throw new RuntimeException("Error generating report", e);
        } finally {
            executor.shutdown();
        }
    }

    private String applyParameters(String query, Map<String, String> params) {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            query = query.replace(":" + entry.getKey(), entry.getValue());
        }
        return query;
    }

    private void validateHeaders(List<String> headers, List<Map<String, Object>> data) {
        if (data.isEmpty()) return;
        Set<String> keys = data.get(0).keySet();
        if (!keys.containsAll(headers)) {
            throw new IllegalArgumentException("Headers do not match result fields");
        }
    }
}




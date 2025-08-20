package com.smartfin.expense_report.generator;

import java.util.List;
import java.util.Map;

public class TxtGenerator {

    public static byte[] generateTxt(List<Map<String, Object>> data, List<String> headers) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.join("\t", headers)).append("\n");

        for (Map<String, Object> row : data) {
            for (String header : headers) {
                sb.append(row.getOrDefault(header, "")).append("\t");
            }
            sb.append("\n");
        }

        return sb.toString().getBytes();
    }
}

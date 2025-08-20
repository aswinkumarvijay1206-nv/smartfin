package com.smartfin.expense_report.util;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Component
public class QueryLoader {
    private final Map<String, String> queryMap = new HashMap<>();

    @PostConstruct
    public void loadQueries() {
        try {
            File file = new File("config/query/queries.xml");
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(file);
            NodeList queries = doc.getElementsByTagName("query");

            for (int i = 0; i < queries.getLength(); i++) {
                Element element = (Element) queries.item(i);
                String name = element.getAttribute("name");
                String content = element.getTextContent().trim();
                queryMap.put(name.toUpperCase(), content);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load queries.xml", e);
        }
    }

    public String getQuery(String name) {
        return queryMap.get(name.toUpperCase());
    }
}

package com.huntos.service;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

@Component
public class CompanyResearchTools {
  
    @Tool("Search the web for information about a company")
    public String searchCompany(String query) {
        // Mocked implementation
        return "Company overview and recent news for " + query;
    }

    @Tool("Get Glassdoor reviews and ratings for a company")
    public String getGlassdoorData(String companyName) {
        // Mocked implementation
        return "Glassdoor reviews for " + companyName + ": 4.5/5 rating, positive culture.";
    }

    @Tool("Find the tech stack used by a company")
    public String getTechStack(String companyName) {
        // Mocked implementation
        return "Tech stack for " + companyName + ": Java, Spring Boot, Angular, PostgreSQL.";
    }
}

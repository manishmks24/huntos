package com.huntos.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "company_research")
public class CompanyResearch {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    
    private String companyName;
    private String overview;
    private String techStack;
    private String cultureKeywords;
    private String topInterviewQuestions;
    private String recentNews;
    private String talkingPoints;
    private LocalDateTime researchedAt;
}

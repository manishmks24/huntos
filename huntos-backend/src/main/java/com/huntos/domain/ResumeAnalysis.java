package com.huntos.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "resume_analyses")
public class ResumeAnalysis {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    
    private UUID resumeId;
    private UUID jobId;
    private int atsScore;
    private String overallGrade;
    
    @ElementCollection
    private List<String> keywordHits;
    
    @ElementCollection
    private List<String> keywordMisses;
    
    @ElementCollection
    private List<String> quickWins;
    
    private String bulletRewritesSuggestions;
    private LocalDateTime analyzedAt;
}

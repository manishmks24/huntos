package com.huntos.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    private String title;
    private String company;
    private String location;
    private String salary;
    
    @Column(length = 2048)
    private String applyUrl;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    private String platform; // linkedin, naukri, indeed, wellfound
    private int matchScore; // AI-generated 0-100
    
    @ElementCollection
    private List<String> skillMatch;
    
    @ElementCollection
    private List<String> skillGaps;
    
    @Column(columnDefinition = "TEXT")
    private String summary; // 2-sentence AI summary
    
    @Column(columnDefinition = "TEXT")
    private String redFlags;
    
    private LocalDateTime scrapedAt;
    private LocalDate digestDate;
    private boolean saved;
}

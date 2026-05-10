package com.huntos.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.UUID;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    private String name;
    private String email;
    private String targetRole;
    
    @ElementCollection
    private List<String> skills;
    
    private int experienceYears;
    private String jobType; // "full-time,remote,hybrid"
    private String preferredLocations; // comma-separated
    private String salaryExpectation;
    private String excludedCompanies;
}

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
public class DailyDigest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    private LocalDate date;
    
    @ManyToMany
    private List<Job> jobs;
    
    private int totalFound;
    private LocalDateTime createdAt;
}

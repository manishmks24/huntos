package com.huntos.repository;

import com.huntos.domain.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID> {
    List<Job> findByDigestDateOrderByMatchScoreDesc(LocalDate digestDate);
    List<Job> findBySavedTrue();
}

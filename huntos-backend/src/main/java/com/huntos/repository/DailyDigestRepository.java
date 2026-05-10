package com.huntos.repository;

import com.huntos.domain.DailyDigest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface DailyDigestRepository extends JpaRepository<DailyDigest, UUID> {
    DailyDigest findTopByOrderByCreatedAtDesc();
}

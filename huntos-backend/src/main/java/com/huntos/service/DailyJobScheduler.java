package com.huntos.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DailyJobScheduler {

    @Scheduled(cron = "${scheduler.digest.cron}", zone = "Asia/Kolkata")
    public void runDailyDigest() {
        // 1. Fetch user profiles
        // 2. Scrape all sources per profile
        // 3. Deduplicate by title+company
        // 4. Score all jobs with AI
        // 5. Sort by matchScore desc, take top 20
        // 6. Save to DailyDigest
        // 7. Send email via JavaMailSender
    }
}

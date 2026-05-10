package com.huntos.controller;

import com.huntos.domain.Job;
import com.huntos.domain.UserProfile;
import com.huntos.repository.JobRepository;
import com.huntos.repository.UserProfileRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class HuntOSController {

    private final JobRepository jobRepository;
    private final UserProfileRepository userProfileRepository;

    public HuntOSController(JobRepository jobRepository, UserProfileRepository userProfileRepository) {
        this.jobRepository = jobRepository;
        this.userProfileRepository = userProfileRepository;
    }

    @PostMapping("/profile")
    public UserProfile saveProfile(@RequestBody UserProfile profile) {
        return userProfileRepository.save(profile);
    }

    @GetMapping("/profile")
    public UserProfile getProfile() {
        return userProfileRepository.findAll().stream().findFirst().orElse(null);
    }

    @GetMapping("/jobs/today")
    public List<Job> getTodayJobs() {
        return jobRepository.findByDigestDateOrderByMatchScoreDesc(LocalDate.now());
    }

    @GetMapping("/jobs/{id}")
    public Job getJobDetail(@PathVariable UUID id) {
        return jobRepository.findById(id).orElse(null);
    }

    @PostMapping("/jobs/{id}/save")
    public void saveJob(@PathVariable UUID id) {
        jobRepository.findById(id).ifPresent(job -> {
            job.setSaved(true);
            jobRepository.save(job);
        });
    }

    @GetMapping("/jobs/saved")
    public List<Job> getSavedJobs() {
        return jobRepository.findBySavedTrue();
    }

    @PostMapping("/search/trigger")
    public void triggerSearch() {
        // Trigger manual search
    }

    @GetMapping("/digest/latest")
    public Object getLatestDigest() {
        return null;
    }
}

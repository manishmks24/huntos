package com.huntos.controller;

import com.huntos.domain.Resume;
import com.huntos.domain.ResumeAnalysis;
import com.huntos.service.AtsScoreService;
import com.huntos.service.ResumeParsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/resume")
public class ResumeController {

    private final ResumeParsService resumeParsService;
    private final AtsScoreService atsScoreService;

    public ResumeController(ResumeParsService resumeParsService, AtsScoreService atsScoreService) {
        this.resumeParsService = resumeParsService;
        this.atsScoreService = atsScoreService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Resume> uploadResume(@RequestParam("file") MultipartFile file) throws IOException {
        String parsedText = resumeParsService.extractText(file);
        
        Resume resume = new Resume();
        resume.setId(UUID.randomUUID());
        resume.setFileName(file.getOriginalFilename());
        resume.setParsedText(parsedText);
        resume.setUploadedAt(LocalDateTime.now());
        resume.setActive(true);
        resume.setVersion("v1");
        
        // Normally we'd save this to a database
        return ResponseEntity.ok(resume);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Resume>> listResumes() {
        // Mock list
        return ResponseEntity.ok(Collections.emptyList());
    }

    @PostMapping("/{id}/analyze/{jobId}")
    public ResponseEntity<ResumeAnalysis> analyzeResume(@PathVariable UUID id, @PathVariable UUID jobId, @RequestBody String jobDescription) {
        // Mocking resume retrieval
        String dummyResumeText = "Senior Java Developer with 5 years of experience...";
        ResumeAnalysis analysis = atsScoreService.analyze(dummyResumeText, jobDescription);
        analysis.setResumeId(id);
        analysis.setJobId(jobId);
        
        return ResponseEntity.ok(analysis);
    }

    @GetMapping("/analysis/{jobId}")
    public ResponseEntity<ResumeAnalysis> getAnalysis(@PathVariable UUID jobId) {
        // Mock retrieval
        return ResponseEntity.ok(new ResumeAnalysis());
    }

    @PostMapping("/{id}/tailor/{jobId}")
    public ResponseEntity<String> tailorResume(@PathVariable UUID id, @PathVariable UUID jobId) {
        // Mock tailored resume PDF/text generation
        return ResponseEntity.ok("Tailored resume generated");
    }
}

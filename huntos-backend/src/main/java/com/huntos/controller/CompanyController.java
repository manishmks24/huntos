package com.huntos.controller;

import com.huntos.service.CompanyResearchAgent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CompanyController {

    private final CompanyResearchAgent companyResearchAgent;

    public CompanyController(CompanyResearchAgent companyResearchAgent) {
        this.companyResearchAgent = companyResearchAgent;
    }

    @GetMapping("/company/{name}/research")
    public ResponseEntity<String> getCompanyResearch(@PathVariable String name) {
        String researchResult = companyResearchAgent.research(name);
        return ResponseEntity.ok(researchResult);
    }

    @GetMapping("/jobs/{id}/apply-strategy")
    public ResponseEntity<String> getApplyStrategy(@PathVariable String id) {
        // Mock apply strategy
        return ResponseEntity.ok("{ \"strategy\": \"Apply directly on company website and reach out to the recruiter on LinkedIn.\" }");
    }
}

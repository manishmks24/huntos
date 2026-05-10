package com.huntos.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huntos.domain.ResumeAnalysis;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AtsScoreService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    public AtsScoreService(ChatClient.Builder chatClientBuilder, ObjectMapper objectMapper) {
        this.chatClient = chatClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    public ResumeAnalysis analyze(String resumeText, String jobDescription) {
        String prompt = """
          Act as an ATS system. Score this resume against the job description.
          
          Resume: %s
          Job Description: %s
          
          Return ONLY this JSON:
          {
            "ats_score": 0-100,
            "overall_grade": "A|B|C|D",
            "keyword_hits": ["skill1", "skill2"],
            "keyword_misses": ["skill3", "skill4"],
            "quick_wins": ["Add Docker to skills section", ...],
            "bullet_rewrites": [
              {"original": "...", "improved": "...", "reason": "..."}
            ]
          }
        """.formatted(resumeText, jobDescription);

        String json = chatClient.prompt().user(prompt).call().content();
        return parseAnalysis(json);
    }

    private ResumeAnalysis parseAnalysis(String json) {
        try {
            JsonNode root = objectMapper.readTree(json);
            ResumeAnalysis analysis = new ResumeAnalysis();
            
            if (root.has("ats_score")) {
                analysis.setAtsScore(root.get("ats_score").asInt());
            }
            if (root.has("overall_grade")) {
                analysis.setOverallGrade(root.get("overall_grade").asText());
            }
            
            analysis.setKeywordHits(extractList(root, "keyword_hits"));
            analysis.setKeywordMisses(extractList(root, "keyword_misses"));
            analysis.setQuickWins(extractList(root, "quick_wins"));
            
            if (root.has("bullet_rewrites")) {
                analysis.setBulletRewritesSuggestions(root.get("bullet_rewrites").toString());
            }
            
            analysis.setAnalyzedAt(LocalDateTime.now());
            return analysis;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse AI response: " + json, e);
        }
    }

    private List<String> extractList(JsonNode root, String fieldName) {
        List<String> list = new ArrayList<>();
        if (root.has(fieldName) && root.get(fieldName).isArray()) {
            for (JsonNode node : root.get(fieldName)) {
                list.add(node.asText());
            }
        }
        return list;
    }
}

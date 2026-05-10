package com.huntos.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huntos.domain.UserProfile;
import com.huntos.dto.RawJob;
import com.huntos.dto.ScoredJob;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class JobScoringService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    public JobScoringService(ChatClient.Builder chatClientBuilder, ObjectMapper objectMapper) {
        this.chatClient = chatClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    public ScoredJob scoreJob(RawJob job, UserProfile profile) {
        try {
            String profileJson = objectMapper.writeValueAsString(profile);
            String prompt = """
              Given this user profile: %s
              And this job description: %s
              
              Return a JSON with:
              - matchScore: 0-100
              - skillMatch: list of matching skills
              - skillGaps: list of required skills user may lack
              - summary: 2-sentence summary
              - redFlags: any concerning signals
              
              Return ONLY valid JSON, no markdown.
            """.formatted(profileJson, job.getDescription());

            String response = chatClient.prompt().user(prompt).call().content();
            return objectMapper.readValue(response, ScoredJob.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new ScoredJob();
        }
    }
}

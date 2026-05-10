package com.huntos.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface CompanyResearchAgent {
  
    @SystemMessage("""
        You are a company research specialist. 
        Research the given company thoroughly for a job interview preparation.
        Use your tools to gather: overview, tech stack, culture, top interview questions, recent news.
        Always return structured JSON.
    """)
    String research(@UserMessage String request);
}

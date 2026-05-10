package com.huntos;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = { dev.langchain4j.openai.spring.AutoConfig.class })
@EnableScheduling
public class HuntOSApplication {

    public static void main(String[] args) {
        SpringApplication.run(HuntOSApplication.class, args);
    }

    @Bean
    public ChatLanguageModel langchainChatModel(
            @Value("${langchain4j.open-ai.chat-model.api-key:default-key}") String apiKey) {
        return OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gpt-3.5-turbo")
                .build();
    }
}

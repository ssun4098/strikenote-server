package com.ssun.strikenoteserver.game.client;

import com.ssun.strikenoteserver.game.dto.GameAnalysisResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GameAiClientTest {

    @Autowired
    private GameAiClient gameAiClient;

    @Test
    void analyzeGame() throws IOException {
        Resource resource = new ClassPathResource("static/game/example.webp");
        GameAnalysisResponse result = gameAiClient.analyzeGame(resource.getContentAsByteArray());
        assertThat(result).isNotNull();
    }
}
package com.ssun.strikenoteserver.game.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssun.strikenoteserver.game.dto.GameAnalysisResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.content.Media;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import java.net.URI;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GameAiClient {
    private final ChatModel chatModel;
    private final ObjectMapper objectMapper;

    private static final String PROMPT = """
            Respond strictly in JSON format as shown below.
            {
                "result": "success",               // or "failure"
                "gameCount": number,               // total number of games
                "games": [
                    {
                        "totalScore": number,
                        "frames": [
                            {
                                "frameNumber": number,
                                "firstRoll": int, // strike is 10
                                "secondRoll": int, // strike or spare is 10
                                "thirdRoll": int, // strike is 10
                                "frameScore": number,
                                "cumulativeScore": number,
                                "isStrike": boolean,
                                "isSpare": boolean
                            }
                        ],
                        "strikes": number,
                        "spares": number,
                        "analysis": string or null
                    }
                ]
            }
""";

    public GameAnalysisResponse analyzeGame(String image) throws JsonProcessingException {
        UserMessage userMessage = UserMessage.builder()
                .text(PROMPT)
                .media(List.of(new Media(MimeTypeUtils.IMAGE_JPEG, URI.create(image))))
                .build();

        String response = chatModel.call(userMessage);
        return objectMapper.readValue(response, new TypeReference<GameAnalysisResponse>() {});
    }


    public GameAnalysisResponse analyzeGame(byte[] image) throws JsonProcessingException {
        UserMessage userMessage = UserMessage.builder()
                .text(PROMPT)
                .media(List.of(new Media(MimeTypeUtils.IMAGE_JPEG, new ByteArrayResource(image))))
                .build();

        String response = chatModel.call(userMessage);
        log.info("{}", response);
        return objectMapper.readValue(response, new TypeReference<GameAnalysisResponse>() {});
    }

}

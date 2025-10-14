package com.ssun.strikenoteserver.game.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssun.strikenoteserver.game.client.GameAiClient;
import com.ssun.strikenoteserver.game.dto.GameAnalysisResponse;
import com.ssun.strikenoteserver.game.dto.GameCreateRequest;
import com.ssun.strikenoteserver.game.dto.GameCreateResponse;
import com.ssun.strikenoteserver.game.entity.GameEntity;
import com.ssun.strikenoteserver.game.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RequiredArgsConstructor
@Service
public class GameService {
    private final GameRepository gameRepository;
    private final GameAiClient gameAiClient;

    @Transactional
    public GameCreateResponse createGame(GameCreateRequest request) {
        MultipartFile image = request.getImage();

        // 이미지 바이트 배열 추출
        byte[] imageBytes;
        try {
            imageBytes = image.getBytes();
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to read game image file", e);
        }

        // AI 분석 수행
        GameAnalysisResponse analysisResponse;
        try {
            analysisResponse = gameAiClient.analyzeGame(imageBytes);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to process AI response", e);
        }

        // AI 분석 실패 처리
        if (!analysisResponse.getResult().equals("success")) {
            return GameCreateResponse.from("failure", List.of());
        }

        // 각 게임을 GameEntity로 변환하여 저장
        List<GameEntity> savedGames = analysisResponse.getGames().stream()
                .map(GameEntity::of)
                .map(gameRepository::save)
                .toList();
        return GameCreateResponse.from("success", savedGames);
    }
}

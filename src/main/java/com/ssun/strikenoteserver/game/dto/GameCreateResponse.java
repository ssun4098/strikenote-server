package com.ssun.strikenoteserver.game.dto;

import com.ssun.strikenoteserver.game.entity.FrameEntity;
import com.ssun.strikenoteserver.game.entity.GameEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class GameCreateResponse {
    private String result;  // "success" or "failure"
    private int gameCount;  // 게임 수
    private List<GameResponse> games;  // 게임 정보 목록

    @Getter
    @Builder
    public static class GameResponse {
        private Long gameId;
        private int totalScore;
        private String title;
        private int strikes;
        private int spares;
        private LocalDateTime createdAt;
        private List<FrameResponse> frames;

        public static GameResponse from(GameEntity game) {
            return GameResponse.builder()
                    .gameId(game.getId())
                    .totalScore(game.getTotalScore())
                    .title(game.getTitle())
                    .strikes(game.getStrikes())
                    .spares(game.getSpares())
                    .createdAt(game.getCreatedAt())
                    .frames(game.getFrames().stream()
                            .map(FrameResponse::from)
                            .toList())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class FrameResponse {
        private Long frameId;
        private int frameNumber;
        private int firstScore;
        private int secondScore;
        private int thirdScore;
        private int sumScore;
        private boolean isStrike;
        private boolean isSpare;

        public static FrameResponse from(FrameEntity frame) {
            return FrameResponse.builder()
                    .frameId(frame.getId())
                    .frameNumber(frame.getFrameNumber())
                    .firstScore(frame.getFirstScore())
                    .secondScore(frame.getSecondScore())
                    .thirdScore(frame.getThirdScore())
                    .sumScore(frame.getSumScore())
                    .isStrike(frame.isStrike())
                    .isSpare(frame.isSpare())
                    .build();
        }
    }

    public static GameCreateResponse from(String result, List<GameEntity> games) {
        return GameCreateResponse.builder()
                .result(result)
                .gameCount(games.size())
                .games(games.stream()
                        .map(GameResponse::from)
                        .toList())
                .build();
    }
}

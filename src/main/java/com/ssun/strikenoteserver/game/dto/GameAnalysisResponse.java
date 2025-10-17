package com.ssun.strikenoteserver.game.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
public class GameAnalysisResponse {
    @JsonProperty("result")
    private String result;  // "success" or "failure"
    @JsonProperty("gameCount")
    private Integer gameCount;
    @JsonProperty("games")
    private List<GameAnalysisDto> games;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GameAnalysisDto {
        @JsonProperty("totalScore")
        private Integer totalScore;

        @JsonProperty("frames")
        private List<FrameDto> frames;

        @JsonProperty("strikes")
        private Integer strikes;

        @JsonProperty("spares")
        private Integer spares;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FrameDto {
        @JsonProperty("frameNumber")
        private int frameNumber;

        @JsonProperty("firstRoll")
        private int firstRoll;

        @JsonProperty("secondRoll")
        private int secondRoll;

        @JsonProperty("thirdRoll")
        private int thirdRoll; // 10번째 프레임용

        @JsonProperty("frameScore")
        private int frameScore;

        @JsonProperty("cumulativeScore")
        private int cumulativeScore;
    }
}

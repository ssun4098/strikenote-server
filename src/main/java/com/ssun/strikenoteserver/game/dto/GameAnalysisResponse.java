package com.ssun.strikenoteserver.game.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

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
    public static class GameAnalysisDto {
        @JsonProperty("totalScore")
        private Integer totalScore;

        @JsonProperty("frames")
        private List<FrameDto> frames;

        @JsonProperty("strikes")
        private Integer strikes;

        @JsonProperty("spares")
        private Integer spares;

        @JsonProperty("analysis")
        private String analysis;
    }

    @Getter
    public static class FrameDto {
        @JsonProperty("frameNumber")
        private Integer frameNumber;

        @JsonProperty("firstRoll")
        private String firstRoll;

        @JsonProperty("secondRoll")
        private String secondRoll;

        @JsonProperty("thirdRoll")
        private String thirdRoll; // 10번째 프레임용

        @JsonProperty("frameScore")
        private Integer frameScore;

        @JsonProperty("cumulativeScore")
        private Integer cumulativeScore;

        @JsonProperty("isStrike")
        private Boolean isStrike;

        @JsonProperty("isSpare")
        private Boolean isSpare;
    }
}

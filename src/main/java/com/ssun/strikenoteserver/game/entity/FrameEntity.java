package com.ssun.strikenoteserver.game.entity;

import com.ssun.strikenoteserver.game.dto.GameAnalysisResponse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class FrameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "frame_id")
    private Long id;

    @Column(name = "frame_number", nullable = false)
    private int frameNumber;

    @Column(name = "first_score", nullable = false)
    private int firstScore;

    @Column(name = "second_score", nullable = false)
    private int secondScore;

    @Column(name = "third_score", nullable = false)
    private int thirdScore;

    @Column(name = "sum_score", nullable = false)
    private int sumScore;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // TODO BaseEntity 처리

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt; // TODO BaseEntity 처리

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private GameEntity game;

    public boolean isStrike() {
        return firstScore == 10;
    }

    public boolean isSpare() {
        return !this.isStrike() && secondScore + firstScore == 10;
    }

    @Builder
    private FrameEntity(int frameNumber, int firstScore, int secondScore, int thirdScore, int sumScore, GameEntity game) {
        this.frameNumber = frameNumber;
        this.firstScore = firstScore;
        this.secondScore = secondScore;
        this.thirdScore = thirdScore;
        this.sumScore = sumScore;
        this.game = game;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public static FrameEntity of(GameEntity game, GameAnalysisResponse.FrameDto dto) {
        if(dto.getFrameNumber() < 1 || dto.getFrameNumber() > 12) {
            throw new IllegalArgumentException("frameNumber must be between 1 and 12");
        }
        if(dto.getFirstRoll() < 0 || dto.getFirstRoll() > 10) {
            throw new IllegalArgumentException("firstScore must be between 0 and 10");
        }

        if(dto.getSecondRoll() < 0 || dto.getSecondRoll() > 10) {
            throw new IllegalArgumentException("secondScore must be between 0 and 10");
        }

        if(dto.getThirdRoll() < 0 || dto.getThirdRoll() > 10) {
            throw new IllegalArgumentException("secondScore must be between 0 and 10");
        }

        return FrameEntity.builder()
                .game(game)
                .frameNumber(dto.getFrameNumber())
                .firstScore(dto.getFirstRoll())
                .secondScore(dto.getSecondRoll())
                .thirdScore(dto.getThirdRoll())
                .sumScore(dto.getCumulativeScore())
                .build();
    }
}

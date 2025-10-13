package com.ssun.strikenoteserver.game.entity;

import com.ssun.strikenoteserver.game.dto.GameAnalysisResponse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "game")
@Entity
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Long id;

    @Column(name = "total_score", nullable = false)
    private int totalScore;

    @Column(name = "title", length = 256, nullable = false)
    private String title;

    @Column(name = "strikes", nullable = false)
    private int strikes;

    @Column(name = "spares", nullable = false)
    private int spares;

    @Column(name = "is_delete", nullable = false)
    private boolean isDelete;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<FrameEntity> frames = new ArrayList<>();

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // TODO BaseEntity 처리

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt; // TODO BaseEntity 처리


    private static String createDefaultTitle() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " 게임";
    }

    public boolean isDelete() {
        return this.isDelete;
    }

    public void delete() {
        this.isDelete = true;
    }

    public void addFrames(List<FrameEntity> frames) {
        this.frames.addAll(frames);
    }

    @Builder
    private GameEntity(int totalScore, String title, int strikes, int spares) {
        this.totalScore = totalScore;
        this.strikes = strikes;
        this.spares = spares;
        this.title = title;
        this.isDelete = false;
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    public static GameEntity of(GameAnalysisResponse.GameAnalysisDto response) {
        GameEntity game = GameEntity.builder()
                .totalScore(response.getTotalScore())
                .strikes(response.getStrikes())
                .spares(response.getSpares())
                .title(createDefaultTitle())
                .build();

        List<FrameEntity> frames = response.getFrames().stream()
                .map(dto -> FrameEntity.of(game, dto))
                .toList();

        game.addFrames(frames);

        return game;
    }
}

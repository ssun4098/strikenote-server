package com.ssun.strikenoteserver.game.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssun.strikenoteserver.game.entity.GameEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class GameSearchResponse {
    private Long id;
    private String title;
    private int strikes;
    private int spares;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    public static GameSearchResponse fromEntity(GameEntity gameEntity) {
        return GameSearchResponse.builder()
                .id(gameEntity.getId())
                .title(gameEntity.getTitle())
                .strikes(gameEntity.getStrikes())
                .spares(gameEntity.getSpares())
                .createdAt(gameEntity.getCreatedAt())
                .build();
    }
}

package com.ssun.strikenoteserver.game.repository;

import com.ssun.strikenoteserver.game.dto.GameAnalysisResponse;
import com.ssun.strikenoteserver.game.entity.GameEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class GameRepositoryTest {
    @Autowired
    private GameRepository gameRepository;
    private final int FIRST_ROLL = 5;
    private final int SECOND_ROLL = 4;
    private final int THIRD_ROLL = 0;

    @Test
    void saveDefaultGame() {
        //given
        List<GameAnalysisResponse.FrameDto> frames = new ArrayList<>();
        int cumulativeScore = 0;
        for(int i = 1; i <= 10; i++) {

            int frameScore = FIRST_ROLL + SECOND_ROLL + THIRD_ROLL;
            cumulativeScore += frameScore;
            frames.add(new GameAnalysisResponse.FrameDto(
                        i,
                    FIRST_ROLL,
                    SECOND_ROLL,
                    THIRD_ROLL,
                    frameScore,
                    cumulativeScore
                    )
            );
        }
        GameAnalysisResponse.GameAnalysisDto game = new GameAnalysisResponse.GameAnalysisDto(1, frames, 0, 0);
        //when
        GameEntity result = gameRepository.save(GameEntity.of(game));

        //then
        assertThat(result.getId()).isNotNull();
    }
}
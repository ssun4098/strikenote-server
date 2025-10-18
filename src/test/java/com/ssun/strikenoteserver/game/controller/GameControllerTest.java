package com.ssun.strikenoteserver.game.controller;

import com.ssun.strikenoteserver.game.dto.GameCreateRequest;
import com.ssun.strikenoteserver.game.dto.GameCreateResponse;
import com.ssun.strikenoteserver.game.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@WebMvcTest(GameController.class)
class GameControllerTest {
     @MockitoBean
     private GameService gameService;

     @Autowired
     private MockMvc mockMvc;

     @Test
     void testCreateGame() throws Exception {
          //given
          Resource resource = new ClassPathResource("static/game/example.webp");
          GameCreateRequest request = new GameCreateRequest(new MockMultipartFile("example.webp", resource.getContentAsByteArray()));

          int totalScore = 193;
          int strikes = 6;
          int spares = 3;
          String gameTitle = "gameTitle";
          String result = "success";
          LocalDateTime createdAt  = LocalDateTime.now(Clock.fixed(Instant.parse("2025-01-01T00:00:00Z"), ZoneId.of("UTC")));

          given(gameService.createGame(any(GameCreateRequest.class)))
                  .willReturn(GameCreateResponse.builder()
                          .gameCount(1)
                          .result(result)
                          .games(List.of(GameCreateResponse.GameResponse.builder()
                                          .gameId(1L)
                                          .title(gameTitle)
                                          .totalScore(totalScore)
                                          .createdAt(createdAt)
                                          .strikes(6)
                                          .spares(3)
                                          .frames(List.of(GameCreateResponse.FrameResponse.builder()
                                                          .frameId(1L)
                                                          .frameNumber(1)
                                                          .firstScore(7)
                                                          .secondScore(3)
                                                          .isSpare(true)
                                                          .build(),
                                                          GameCreateResponse.FrameResponse.builder()
                                                                  .frameId(2L)
                                                                  .frameNumber(2)
                                                                  .firstScore(10)
                                                                  .secondScore(0)
                                                                  .isStrike(true)
                                                                  .build(),
                                                          GameCreateResponse.FrameResponse.builder()
                                                                  .frameId(3L)
                                                                  .frameNumber(3)
                                                                  .firstScore(7)
                                                                  .secondScore(3)
                                                                  .isSpare(true)
                                                                  .build(),
                                                          GameCreateResponse.FrameResponse.builder()
                                                                  .frameId(4L)
                                                                  .frameNumber(4)
                                                                  .firstScore(10)
                                                                  .secondScore(0)
                                                                  .isStrike(true)
                                                                  .build(),
                                                          GameCreateResponse.FrameResponse.builder()
                                                                  .frameId(5L)
                                                                  .frameNumber(5)
                                                                  .firstScore(10)
                                                                  .secondScore(0)
                                                                  .isStrike(true)
                                                                  .build(),
                                                          GameCreateResponse.FrameResponse.builder()
                                                                  .frameId(6L)
                                                                  .frameNumber(6)
                                                                  .firstScore(9)
                                                                  .secondScore(0)
                                                                  .build(),
                                                          GameCreateResponse.FrameResponse.builder()
                                                                  .frameId(7L)
                                                                  .frameNumber(7)
                                                                  .firstScore(10)
                                                                  .secondScore(0)
                                                                  .isStrike(true)
                                                                  .build(),
                                                          GameCreateResponse.FrameResponse.builder()
                                                                  .frameId(8L)
                                                                  .frameNumber(8)
                                                                  .firstScore(9)
                                                                  .secondScore(0)
                                                                  .build(),
                                                          GameCreateResponse.FrameResponse.builder()
                                                                  .frameId(9L)
                                                                  .frameNumber(9)
                                                                  .firstScore(10)
                                                                  .secondScore(0)
                                                                  .isStrike(true)
                                                                  .build(),
                                                          GameCreateResponse.FrameResponse.builder()
                                                                  .frameId(10L)
                                                                  .frameNumber(10)
                                                                  .firstScore(10)
                                                                  .secondScore(0)
                                                                  .isStrike(true)
                                                                  .build(),
                                                          GameCreateResponse.FrameResponse.builder()
                                                                  .frameId(11L)
                                                                  .frameNumber(11)
                                                                  .firstScore(8)
                                                                  .secondScore(2)
                                                                  .isSpare(true)
                                                                  .build())
                                                  )
                                  .build()))
                          .build());
          //when & then
          mockMvc.perform(multipart("/api/games")
                  .file((MockMultipartFile) request.image())
                  .contentType(MediaType.MULTIPART_FORM_DATA))
                  .andExpect(status().isCreated())
                  .andExpect(jsonPath(("$.result")).value(result))
                  .andExpect(jsonPath(("$.games[0].title")).value(gameTitle))
                  .andExpect(jsonPath(("$.games[0].totalScore")).value(totalScore))
                  .andExpect(jsonPath(("$.games[0].spares")).value(spares))
                  .andExpect(jsonPath(("$.games[0].strikes")).value(strikes))
                  .andExpect(jsonPath(("$.games[0].createdAt")).value("2025-01-01T00:00:00"))

                  .andExpect(jsonPath(("$.games[0].frames[0].frameId")).value(1L))
                  .andExpect(jsonPath(("$.games[0].frames[0].frameNumber")).value(1))
                  .andExpect(jsonPath(("$.games[0].frames[0].firstScore")).value(7))
                  .andExpect(jsonPath(("$.games[0].frames[0].secondScore")).value(3))
                  .andExpect(jsonPath(("$.games[0].frames[0].strike")).value(false))
                  .andExpect(jsonPath(("$.games[0].frames[0].spare")).value(true))

                  .andExpect(jsonPath(("$.games[0].frames[1].frameId")).value(2L))
                  .andExpect(jsonPath(("$.games[0].frames[1].frameNumber")).value(2))
                  .andExpect(jsonPath(("$.games[0].frames[1].firstScore")).value(10))
                  .andExpect(jsonPath(("$.games[0].frames[1].secondScore")).value(0))
                  .andExpect(jsonPath(("$.games[0].frames[1].strike")).value(true))
                  .andExpect(jsonPath(("$.games[0].frames[1].spare")).value(false))

                  .andExpect(jsonPath(("$.games[0].frames[2].frameId")).value(3L))
                  .andExpect(jsonPath(("$.games[0].frames[2].frameNumber")).value(3))
                  .andExpect(jsonPath(("$.games[0].frames[2].firstScore")).value(7))
                  .andExpect(jsonPath(("$.games[0].frames[2].secondScore")).value(3))
                  .andExpect(jsonPath(("$.games[0].frames[2].strike")).value(false))
                  .andExpect(jsonPath(("$.games[0].frames[2].spare")).value(true))

                  .andExpect(jsonPath(("$.games[0].frames[3].frameId")).value(4L))
                  .andExpect(jsonPath(("$.games[0].frames[3].frameNumber")).value(4))
                  .andExpect(jsonPath(("$.games[0].frames[3].firstScore")).value(10))
                  .andExpect(jsonPath(("$.games[0].frames[3].secondScore")).value(0))
                  .andExpect(jsonPath(("$.games[0].frames[3].strike")).value(true))
                  .andExpect(jsonPath(("$.games[0].frames[3].spare")).value(false))

                  .andExpect(jsonPath(("$.games[0].frames[4].frameId")).value(5L))
                  .andExpect(jsonPath(("$.games[0].frames[4].frameNumber")).value(5))
                  .andExpect(jsonPath(("$.games[0].frames[4].firstScore")).value(10))
                  .andExpect(jsonPath(("$.games[0].frames[4].secondScore")).value(0))
                  .andExpect(jsonPath(("$.games[0].frames[4].strike")).value(true))
                  .andExpect(jsonPath(("$.games[0].frames[4].spare")).value(false))

                  .andExpect(jsonPath(("$.games[0].frames[5].frameId")).value(6L))
                  .andExpect(jsonPath(("$.games[0].frames[5].frameNumber")).value(6))
                  .andExpect(jsonPath(("$.games[0].frames[5].firstScore")).value(9))
                  .andExpect(jsonPath(("$.games[0].frames[5].secondScore")).value(0))
                  .andExpect(jsonPath(("$.games[0].frames[5].strike")).value(false))
                  .andExpect(jsonPath(("$.games[0].frames[5].spare")).value(false))

                  .andExpect(jsonPath(("$.games[0].frames[6].frameId")).value(7L))
                  .andExpect(jsonPath(("$.games[0].frames[6].frameNumber")).value(7))
                  .andExpect(jsonPath(("$.games[0].frames[6].firstScore")).value(10))
                  .andExpect(jsonPath(("$.games[0].frames[6].secondScore")).value(0))
                  .andExpect(jsonPath(("$.games[0].frames[6].strike")).value(true))
                  .andExpect(jsonPath(("$.games[0].frames[6].spare")).value(false))

                  .andExpect(jsonPath(("$.games[0].frames[7].frameId")).value(8L))
                  .andExpect(jsonPath(("$.games[0].frames[7].frameNumber")).value(8))
                  .andExpect(jsonPath(("$.games[0].frames[7].firstScore")).value(9))
                  .andExpect(jsonPath(("$.games[0].frames[7].secondScore")).value(0))
                  .andExpect(jsonPath(("$.games[0].frames[7].strike")).value(false))
                  .andExpect(jsonPath(("$.games[0].frames[7].spare")).value(false))

                  .andExpect(jsonPath(("$.games[0].frames[8].frameId")).value(9L))
                  .andExpect(jsonPath(("$.games[0].frames[8].frameNumber")).value(9))
                  .andExpect(jsonPath(("$.games[0].frames[8].firstScore")).value(10))
                  .andExpect(jsonPath(("$.games[0].frames[8].secondScore")).value(0))
                  .andExpect(jsonPath(("$.games[0].frames[8].strike")).value(true))
                  .andExpect(jsonPath(("$.games[0].frames[8].spare")).value(false))

                  .andExpect(jsonPath(("$.games[0].frames[9].frameId")).value(10L))
                  .andExpect(jsonPath(("$.games[0].frames[9].frameNumber")).value(10))
                  .andExpect(jsonPath(("$.games[0].frames[9].firstScore")).value(10))
                  .andExpect(jsonPath(("$.games[0].frames[9].secondScore")).value(0))
                  .andExpect(jsonPath(("$.games[0].frames[9].strike")).value(true))
                  .andExpect(jsonPath(("$.games[0].frames[9].spare")).value(false))

                  .andExpect(jsonPath(("$.games[0].frames[10].frameId")).value(11L))
                  .andExpect(jsonPath(("$.games[0].frames[10].frameNumber")).value(11))
                  .andExpect(jsonPath(("$.games[0].frames[10].firstScore")).value(8))
                  .andExpect(jsonPath(("$.games[0].frames[10].secondScore")).value(2))
                  .andExpect(jsonPath(("$.games[0].frames[10].strike")).value(false))
                  .andExpect(jsonPath(("$.games[0].frames[10].spare")).value(true))

                  .andDo(document("game-create"));


     }
}
package com.ssun.strikenoteserver.game.controller;

import com.ssun.strikenoteserver.game.dto.GameCreateRequest;
import com.ssun.strikenoteserver.game.dto.GameCreateResponse;
import com.ssun.strikenoteserver.game.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/games")
public class GameController {
    private final GameService gameService;

    @PostMapping
    public ResponseEntity<GameCreateResponse> createGame(GameCreateRequest request)  {
        return ResponseEntity.status(HttpStatus.CREATED).body(gameService.createGame(request));
    }
}

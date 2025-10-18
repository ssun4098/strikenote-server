package com.ssun.strikenoteserver.game.controller;

import com.ssun.strikenoteserver.game.dto.GameCreateRequest;
import com.ssun.strikenoteserver.game.dto.GameCreateResponse;
import com.ssun.strikenoteserver.game.dto.GameSearchResponse;
import com.ssun.strikenoteserver.game.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/games")
public class GameController {
    private final GameService gameService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GameCreateResponse> createGame(@ModelAttribute GameCreateRequest request)  {
        return ResponseEntity.status(HttpStatus.CREATED).body(gameService.createGame(request));
    }

    @GetMapping
    public ResponseEntity<List<GameSearchResponse>> searchGames(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(gameService.searchGames(pageable));
    }
}

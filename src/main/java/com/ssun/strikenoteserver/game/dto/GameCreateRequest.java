package com.ssun.strikenoteserver.game.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class GameCreateRequest {
    private MultipartFile image;
}

package com.ssun.strikenoteserver.game.dto;

import org.springframework.web.multipart.MultipartFile;


public record GameCreateRequest(MultipartFile image) {}

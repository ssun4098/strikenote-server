package com.ssun.strikenoteserver.game.repository;

import com.ssun.strikenoteserver.game.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<GameEntity, Long> {
}

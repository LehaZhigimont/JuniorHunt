package com.example.juniorhunt.repository;

import com.example.juniorhunt.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

    Position findByPosition(String position);
}

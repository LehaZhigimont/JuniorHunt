package com.example.juniorhunt.service;

import com.example.juniorhunt.entity.Position;
import com.example.juniorhunt.entity.Skill;
import com.example.juniorhunt.repository.PositionRepository;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class PositionService {
    private final Logger logger = Logger.getLogger(PositionService.class.getName());

    private final PositionRepository positionRepository;

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public void updatePositionSkills(Long id, Skill skill) {
        Position position = positionRepository.findById(id).orElse(null);
        assert position != null;
        logger.info("updatePositionSkills:");
        logger.info("position = " + position.getPosition());
        logger.info("skill = " + skill.getSkill());
        position.getSkills().add(skill);
        positionRepository.save(position);
    }

    public void removePositionSkills(Long id, Skill skill) {
        Position position = positionRepository.findById(id).orElse(null);
        assert position != null;
        logger.info("removePositionSkills:");
        logger.info("position = " + position.getPosition());
        logger.info("skill = " + skill.getSkill());
        position.getSkills().remove(skill);
        positionRepository.save(position);
    }

}

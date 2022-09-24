package com.example.juniorhunt.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "skill")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String skill;
    @Enumerated(value = EnumType.STRING)
    private SkillType type;

    public Skill(String skill, SkillType type) {
        this.skill = skill;
        this.type = type;
    }

    public Skill(String skill, SkillType type, Position position) {
        this.skill = skill;
        this.type = type;
    }


}

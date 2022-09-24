package com.example.juniorhunt.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String position;

    @OneToMany
    private Set<Skill> skills;

    public Position(String position) {
        this.position = position;
    }

//    public void addUserForPosition(User user) {
//        users.add(user);
//        user.setPosition(this);
//    }
//
//    public void removeUserPosition(User user) {
//        users.remove(user);
//        user.setPosition(null);
//    }

    @Override
    public String toString() {
        return getPosition();
    }

}

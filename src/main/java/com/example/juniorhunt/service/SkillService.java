package com.example.juniorhunt.service;

import com.example.juniorhunt.entity.Skill;
import com.example.juniorhunt.entity.SkillType;
import com.example.juniorhunt.entity.User;
import com.example.juniorhunt.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public void allSkills() {

    }

    public List<String> typeSkills(String skill) {
        return skillRepository.findAll().stream().filter(u -> u.getType().getName().equals(skill))
                .map(Skill::getSkill).toList();
    }

    public List<String> typeSkills(String skill, Set<Skill> userSkills) {
        List<Skill> list = new java.util.ArrayList<>(skillRepository.findAll().stream().filter(u -> u.getType().getName().equals(skill)).toList());



        list.removeIf(userSkills::contains);

        Iterator<Skill> iterator = list.iterator();
        while(iterator.hasNext()){
            if (userSkills.contains(iterator.next())){
                System.out.println(iterator);
                iterator.remove();
            }
        }



        return list.stream().map(Skill::getSkill).toList();
    }

    public void saveSkills(Map<String, String> dataSkills, User user) {

        final String skillType = dataSkills.get("skill");
        final SkillType type = SkillType.valueOf(skillType);
        final List<Skill> typeList = skillRepository.findAll().stream().filter(u -> u.getType().equals(type)).toList();

        for (Skill skill : typeList) {
            if (dataSkills.containsKey(skill.getSkill()))
                user.getSkills().add(skill);
            else
                user.getSkills().remove(skill);
        }
    }
}

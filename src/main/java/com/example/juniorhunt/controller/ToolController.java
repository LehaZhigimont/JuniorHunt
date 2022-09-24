package com.example.juniorhunt.controller;

import com.example.juniorhunt.entity.Position;
import com.example.juniorhunt.entity.Skill;
import com.example.juniorhunt.entity.SkillType;
import com.example.juniorhunt.repository.PositionRepository;
import com.example.juniorhunt.repository.SkillRepository;
import com.example.juniorhunt.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/tool")
@PreAuthorize("hasAuthority('ADMIN')")
public class ToolController {

    private final String HARDSKILLS = "hardSkills";
    private final String SOFTSKILLS = "softSkills";
    private final String TOOLS = "tools";
    private final String POSITIONS = "positions";
    private final SkillRepository skillRepository;
    private final PositionRepository positionRepository;
    private final UserRepository userRepository;

    public ToolController(SkillRepository skillRepository, PositionRepository positionRepository, UserRepository userRepository) {
        this.skillRepository = skillRepository;
        this.positionRepository = positionRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String toolPage(Model model) {
        List<String> positionList = positionRepository.findAll().stream()
                .map(Position::getPosition).toList();
        model.addAttribute(POSITIONS, positionList);
        model.addAttribute(HARDSKILLS, getAttributeTypeListSkills(SkillType.HARD));
        model.addAttribute(SOFTSKILLS, getAttributeTypeListSkills(SkillType.SOFT));
        model.addAttribute(TOOLS, getAttributeTypeListSkills(SkillType.TOOL));
        model.addAttribute("users", userRepository.findAll());
        return "tool";
    }


    @PostMapping("position")
    public String addPosition(@RequestParam(name = "position", required = false) String positionName,
                          Model model) {
        final Position position = new Position(positionName);
        positionRepository.save(position);
        List<String> positionList = positionRepository.findAll().stream()
                .map(Position::getPosition).toList();
        model.addAttribute(POSITIONS, positionList);
        return "redirect:/tool";
    }


    @PostMapping("hard")
    public String addHardSkill(@RequestParam(name = "hardSkill", required = false) String skillName,
                               Model model) {
        final Skill skill = new Skill(skillName, SkillType.HARD);
        skillRepository.save(skill);
        model.addAttribute(HARDSKILLS, getAttributeTypeListSkills(SkillType.HARD));
        return "redirect:/tool";
    }

    @PostMapping("soft")
    public String addSoftSkill(@RequestParam(name = "softSkill", required = false) String skillName,
                               Model model) {
        final Skill skill = new Skill(skillName, SkillType.SOFT);
        skillRepository.save(skill);

        model.addAttribute(SOFTSKILLS, getAttributeTypeListSkills(SkillType.SOFT));
        return "redirect:/tool";
    }

    @PostMapping("tool")
    public String addTool(@RequestParam(name = "tool", required = false) String skillName,
                          Model model) {
        final Skill skill = new Skill(skillName, SkillType.TOOL);
        skillRepository.save(skill);
        model.addAttribute(TOOLS, getAttributeTypeListSkills(SkillType.TOOL));
        return "redirect:/tool";
    }






    private List<String> getAttributeTypeListSkills(SkillType type) {
        List<String> skills = skillRepository.findAll().stream().filter(u -> u.getType().equals(type))
                    .map(Skill::getSkill).toList();
           return skills;
    }
}

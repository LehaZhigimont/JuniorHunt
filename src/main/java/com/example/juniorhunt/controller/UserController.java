package com.example.juniorhunt.controller;

import com.example.juniorhunt.entity.User;
import com.example.juniorhunt.repository.CountryRepository;
import com.example.juniorhunt.repository.PositionRepository;
import com.example.juniorhunt.repository.SkillRepository;
import com.example.juniorhunt.repository.UserRepository;
import com.example.juniorhunt.service.SkillService;
import com.example.juniorhunt.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequestMapping("/junior")
public class UserController {


    private Logger logger = Logger.getLogger(UserController.class.getName());
    private final SkillRepository skillRepository;
    private final CountryRepository countryRepository;
    private final PositionRepository positionRepository;
    private final UserRepository userRepository;

    private final UserService userService;
    private final SkillService skillService;

    public UserController(SkillRepository skillRepository, CountryRepository countryRepository,
                          PositionRepository positionRepository, UserRepository userRepository, UserService userService, SkillService skillService) {
        this.skillRepository = skillRepository;
        this.countryRepository = countryRepository;
        this.positionRepository = positionRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.skillService = skillService;
    }

    @GetMapping
    public String userPage(HttpServletRequest request, Model model) {
        User user = userRepository.findByUsername(request.getRemoteUser());
        model.addAttribute("hardSkills", skillService.typeSkills("HARD", user.getSkills()));
        model.addAttribute("softSkills", skillService.typeSkills("SOFT", user.getSkills()));
        model.addAttribute("toolSkills", skillService.typeSkills("TOOL", user.getSkills()));

        model.addAttribute("userHardSkills", userService.userListTypeSkills(user, "HARD"));
        model.addAttribute("userSoftSkills", userService.userListTypeSkills(user, "SOFT"));
        model.addAttribute("userToolSkills", userService.userListTypeSkills(user, "TOOL"));
        model.addAttribute("user", user);
        return "junior";
    }


    @PostMapping
    public String addSkills(@RequestParam(name = "skill") String skill,
                            @RequestParam(name = "userId") User user,
                            @RequestParam Map<String, String> checkedSkills,
                            Model model) {

        logger.info("AddHardSkill - POST");

        for (String key : checkedSkills.keySet()
        ) {
            System.out.println(key + " - " + checkedSkills.get(key));
        }

        skillService.saveSkills(checkedSkills, user);
        userRepository.save(user);

        //if (skill.contains(SkillType.HARD.getName())) {
        model.addAttribute("hardSkills", skillService.typeSkills("HARD", user.getSkills()));
        //}
        // if (skill.contains(SkillType.SOFT.getName())) {
        model.addAttribute("softSkills", skillService.typeSkills("SOFT", user.getSkills()));
        // }
        // if (skill.contains(SkillType.TOOL.getName())) {
        model.addAttribute("toolSkills", skillService.typeSkills("TOOL", user.getSkills()));
        model.addAttribute("userHardSkills", userService.userListTypeSkills(user, "HARD"));
        model.addAttribute("userSoftSkills", userService.userListTypeSkills(user, "SOFT"));
        model.addAttribute("userToolSkills", userService.userListTypeSkills(user, "TOOL"));
        // }
        model.addAttribute("user", user);
        return "junior";
    }


    @GetMapping("{username}")
    public String updateForm(@PathVariable(name = "username") String username, Model model) {

        model.addAttribute("user", userRepository.findByUsername(username));
        model.addAttribute("countries", countryRepository.findAll());
        model.addAttribute("positions", positionRepository.findAll());
        return "edit";
    }

    @PostMapping("edit")
    public String updateUser(@RequestParam Map<String, String> userForm,
                             Model model) {
        model.addAttribute("user", userService.saveUser(userForm));
        return "redirect:/junior";
    }

    @PostMapping("aboutMe")
    public String updateAboutMe(@RequestParam Map<String, String> userForm,
                             Model model) {

        for (String s : userForm.keySet()       ) {
            System.out.println(s + " - " + userForm.get(s));
        }
        userService.saveUser(userForm);
        User user = userRepository.findById(Long.valueOf(userForm.get("userId"))).orElse(null);
        model.addAttribute("hardSkills", skillService.typeSkills("HARD", user.getSkills()));
        model.addAttribute("softSkills", skillService.typeSkills("SOFT", user.getSkills()));
        model.addAttribute("toolSkills", skillService.typeSkills("TOOL", user.getSkills()));

        model.addAttribute("userHardSkills", userService.userListTypeSkills(user, "HARD"));
        model.addAttribute("userSoftSkills", userService.userListTypeSkills(user, "SOFT"));
        model.addAttribute("userToolSkills", userService.userListTypeSkills(user, "TOOL"));
        model.addAttribute("user", user);
        return "redirect:/junior";
    }

}

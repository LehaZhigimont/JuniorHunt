package com.example.juniorhunt.controller;

import com.example.juniorhunt.entity.Country;
import com.example.juniorhunt.entity.Role;
import com.example.juniorhunt.entity.User;
import com.example.juniorhunt.repository.CountryRepository;
import com.example.juniorhunt.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.Calendar;
import java.util.Map;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;

    public RegistrationController(UserRepository userRepository, CountryRepository countryRepository) {
        this.userRepository = userRepository;
        this.countryRepository = countryRepository;
    }

    @GetMapping
    public String registration() {
        return "registration";
    }

    @PostMapping
    public String addUser(User user,
                          Model model) {

        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB!=null){
            //model.addAttribute("message", "User exists!");
            model.addAttribute("message", new Date(Calendar.getInstance().getTime().getTime()));
            return "registration";
        }
        user.setActive(true);
        user.setRole(Role.USER);

        String pass = user.getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(pass));
        System.out.println(user.getPassword());

//        User userAdd = new User(user.getUsername(), user.getPassword(), user.getCountry());
//        System.out.println(userAdd.getUsername());
//        System.out.println(userAdd.getPassword());

        System.out.println(user);
        userRepository.save(user);
        return "redirect:/login";
    }
}

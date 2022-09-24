package com.example.juniorhunt.service;

import com.example.juniorhunt.entity.*;
import com.example.juniorhunt.repository.CountryRepository;
import com.example.juniorhunt.repository.PositionRepository;
import com.example.juniorhunt.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private static final String ID = "userId";
    private final String USERNAME = "username";
    private final String EMAIL = "email";
    private final String PASSWORD = "password";
    private final String FIRST_NAME = "firstName";
    private final String LAST_NAME = "lastName";
    private final String PHONE = "phone";
    private final String ROLE = "role";
    private final String CREATED_DATA = "createdData";
    private final String ABOUT = "aboutMe";
    private final String EXPERIENCE = "experience";
    private final String COURSES = "courses";
    private final String LANGUAGE = "language";
    private final String URL_IMG_PROFILE = "urlImgProfile";
    private final String URL_TELEGRAM = "urlTelegram";
    private final String URL_LINKEDIN = "urlLinkedin";
    private final String COUNTRY = "country";
    private final String POSITION = "position";
    private final String ACTION = "action";

    private final List<String> userFields = List.of("email", "password", "firstName", "lastName", "phone",
            "role", "createdDate", "about", "courses", "language", "urlImgProfile", "urlTelegram", "urlLinkedin",
            "country", "position", "action");


    private final UserRepository userRepository;
    private final PositionRepository positionRepository;
    private final CountryRepository countryRepository;

    public UserService(UserRepository userRepository, PositionRepository positionRepository, CountryRepository countryRepository) {
        this.userRepository = userRepository;
        this.positionRepository = positionRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }


    public User saveUser(Map<String, String> dataUser) {

        for (Object s : dataUser.keySet()
        ) {
            System.out.println(s + " - " + dataUser.get(s));
        }

        User user = findUser(Long.valueOf(dataUser.get(ID)));

        if (dataUser.containsKey(EMAIL))
            user.setEmail(dataUser.get(EMAIL));
        if (dataUser.containsKey(FIRST_NAME))
            user.setFirstName(dataUser.get(FIRST_NAME));
        if (dataUser.containsKey(LAST_NAME))
            user.setLastName(dataUser.get(LAST_NAME));
        if (dataUser.containsKey(PHONE))
            user.setPhone(dataUser.get(PHONE));
        if (dataUser.containsKey(ROLE))
            user.setRole(Role.valueOf(dataUser.get(ROLE)));
        if (dataUser.containsKey(ABOUT))
            user.setAboutMe(dataUser.get(ABOUT));
        if (dataUser.containsKey(EXPERIENCE))
            user.setExperience(dataUser.get(EXPERIENCE));
        if (dataUser.containsKey(COURSES))
            user.setCourses(dataUser.get(COURSES));
        if (dataUser.containsKey(LANGUAGE))
            user.setLanguage(findByName(dataUser.get(LANGUAGE)));
        if (dataUser.containsKey(URL_IMG_PROFILE))
            user.setUrlImgProfile(dataUser.get(URL_IMG_PROFILE));
        if (dataUser.containsKey(URL_TELEGRAM))
            user.setUrlTelegram(dataUser.get(URL_TELEGRAM));
        if (dataUser.containsKey(URL_LINKEDIN))
            user.setUrlLinkedin(dataUser.get(URL_LINKEDIN));
        if (dataUser.containsKey(COUNTRY)) {
            Long id = countryRepository.findByCountry(dataUser.get(COUNTRY)).getId();
            Optional<Country> country = countryRepository.findById(id);
            country.ifPresent(user::setCountry);
        }
        if (dataUser.containsKey(ACTION))
            user.setActive(Boolean.parseBoolean(dataUser.get(ACTION)));
        if (dataUser.containsKey(POSITION)) {
            Long id = positionRepository.findByPosition(dataUser.get(POSITION)).getId();
            Optional<Position> position = positionRepository.findById(id);
            position.ifPresent(user::setPosition);
        }
        userRepository.save(user);
        return user;
    }


    public List<String> userListTypeSkills(User user, String type) {
        return user.getSkills().stream().filter(skill -> skill.getType().getName().equals(type))
                .map(Skill::getSkill).toList();
    }

    public User findUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public List<Language> listLang(){
        return Arrays.stream(Language.values()).toList();
    }

    private Language findByName(String name){
        Language language = Arrays.stream(Language.values()).filter(l -> l.getName().equals(name)).findFirst().get();

        System.out.println(language);
        return language;
    }

    public void positionTest(){
        positionRepository.findByPosition("Java").getSkills().forEach(System.out::println);

    }

}

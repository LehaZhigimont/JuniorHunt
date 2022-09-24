package com.example.juniorhunt.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDateTime createdDate = LocalDateTime.now();
    private String aboutMe;
    private String experience;
    private String courses;
    @Enumerated(EnumType.STRING)
    private Language language;
    private String urlImgProfile;
    private String urlTelegram;
    private String urlLinkedin;
    @Enumerated(EnumType.STRING)
    private Role role;

//    @ManyToMany
//    @ToString.Exclude
//    private Set<Skill> skill;

    @ManyToMany
    @JoinTable(name = "user_skill",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    @ToString.Exclude
    private Set<Skill> skills;

    @ManyToOne
    private Country country;

    @OneToOne
    @JoinColumn(name = "position_id")
    private Position position;
    private boolean active;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(getRole());
    }


    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user1 = (User) o;
        return id != null && Objects.equals(id, user1.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

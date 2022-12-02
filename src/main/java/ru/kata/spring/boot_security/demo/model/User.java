package ru.kata.spring.boot_security.demo.model;

import lombok.Getter;
import lombok.Setter;

import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "firstname")
    @Size(min = 2, max = 35, message = "FIRSTNAME field: must be between 2 and 35 characters")
    @NotBlank(message = "FIRSTNAME field: field cannot be empty")
    private String firstName;

    @Column(name = "lastname")
    @Size(min = 2, max = 35, message = "LASTNAME field: must be between 2 and 35 characters")
    @NotBlank(message = "LASTNAME field: field cannot be empty")
    private String lastName;

    @Column(name = "age")
    @Min(value = 18, message = "AGE field: age should been from 18 and more")
    @Max(value = 120, message = "AGE field: age should been from 18 and more")
    private short age;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean isEnabled;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roleSet;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleSet;
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
        return isEnabled;
    }
}

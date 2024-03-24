package com.example.Shop.models;

// User - пользователь, который будет регистрироваться в системе
// создаем модель

import com.example.Shop.dto.Registration;

import com.example.Shop.models.enums.Role;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.HashSet;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Column(name = "name")
    private String name;
    @Column(name = "active")
    private boolean active; // признак активности (флажок или true или false)
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id")
    private Image avatar;
    @Column(name = "password", length = 1000)
    private String password;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"))

//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "user_roles",
//            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
//            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
//    )
//    private List<Role> roles = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>(); // роли пользователей
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    // Свойство mappedBy — это то, что мы используем,
    // чтобы сообщить Hibernate, какую переменную мы используем для представления родительского класса в нашем дочернем классе.
    private List<Product> products = new ArrayList<>();
    private LocalDateTime dateOfCreated; // дата создания пользователя

    @PrePersist
    private void init() {
        dateOfCreated = LocalDateTime.now();
    }

    // security

    // метод isAdmin проверяет, содержит ли его Set роль ROLE_ADMIN,
    // если да. вернет true, если нет - false
    public boolean isAdmin() {
        return roles.contains(Role.ROLE_ADMIN);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return roles;
    }

    @Override
    public String getUsername()
    {
        return email;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return active;
    }
}

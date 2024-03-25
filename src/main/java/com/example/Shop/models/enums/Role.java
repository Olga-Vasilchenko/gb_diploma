package com.example.Shop.models.enums;

import org.springframework.security.core.GrantedAuthority;

// выдаем роли после авторизации на сайте
public enum Role implements GrantedAuthority {
    ROLE_USER, ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
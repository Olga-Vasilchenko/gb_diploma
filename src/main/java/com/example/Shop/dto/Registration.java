package com.example.Shop.dto;

import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class Registration {
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
}

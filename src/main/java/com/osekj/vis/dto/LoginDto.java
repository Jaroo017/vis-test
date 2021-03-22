package com.osekj.vis.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class LoginDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
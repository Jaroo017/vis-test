package com.osekj.vis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class UserCreateDto {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private Long roleId;
}

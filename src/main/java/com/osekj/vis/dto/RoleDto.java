package com.osekj.vis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@AllArgsConstructor
public class RoleDto {
    @NotBlank
    private String name;
    @NotEmpty
    private List<String> permissions;
}

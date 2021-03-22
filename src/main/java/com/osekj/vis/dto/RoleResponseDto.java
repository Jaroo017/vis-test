package com.osekj.vis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RoleResponseDto {

    private Long id;
    private String name;
    private List<String> permissions;
}

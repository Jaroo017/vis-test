package com.osekj.vis.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserEditDto {
    @NotNull
    private String username;
    @NotNull
    private Long roleId;
}

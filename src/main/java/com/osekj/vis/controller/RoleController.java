package com.osekj.vis.controller;

import com.osekj.vis.annotation.RoleAuthorization;
import com.osekj.vis.dto.RoleDto;
import com.osekj.vis.dto.RoleResponseDto;
import com.osekj.vis.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/vis-test/roles")
public class RoleController {

    private final RoleService roleService;

    @GetMapping()
    @ResponseStatus(OK)
    @RoleAuthorization
    public List<RoleResponseDto> getAllRoles() {
        return roleService.getAllRoles();
    }

    @PostMapping()
    @ResponseStatus(CREATED)
    @RoleAuthorization
    public RoleResponseDto createRole(@Valid @RequestBody RoleDto roleDto) {
        return roleService.createRole(roleDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(OK)
    @RoleAuthorization
    public RoleResponseDto updateRole(@PathVariable("id") Long id,
                                      @Valid @RequestBody RoleDto roleDto) {
        return roleService.updateRole(id, roleDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    @RoleAuthorization
    public void deleteRole(@PathVariable("id") Long id) {
        roleService.deleteRole(id);
    }
}

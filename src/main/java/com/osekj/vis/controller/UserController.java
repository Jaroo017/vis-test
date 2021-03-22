package com.osekj.vis.controller;

import com.osekj.vis.dto.UserCreateDto;
import com.osekj.vis.dto.UserEditDto;
import com.osekj.vis.dto.UserResponseDto;
import com.osekj.vis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/vis-test/users")
public class UserController {

    private final UserService userService;

    @GetMapping()
    @ResponseStatus(OK)
    @PreAuthorize("hasAuthority(T(com.osekj.vis.model.enums.PermissionType).LIST_USERS.toGrantedAuthority())")
    public List<UserResponseDto> getUsers() {
        return userService.getUsers();
    }


    @PostMapping()
    @ResponseStatus(CREATED)
    @PreAuthorize("hasAnyAuthority(T(com.osekj.vis.model.enums.PermissionType).CREATE_USERS.toGrantedAuthority())")
    public UserResponseDto addUser(@RequestBody UserCreateDto userDto) {
        return userService.addUser(userDto);
    }


    @PutMapping("/{id}")
    @ResponseStatus(OK)
    @PreAuthorize("hasAuthority(T(com.osekj.vis.model.enums.PermissionType).EDIT_USERS.toGrantedAuthority())")
    public UserResponseDto editUser(@PathVariable("id") Long id,
                                    @RequestBody UserEditDto userEditDto) {
        return userService.editUser(id, userEditDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    @PreAuthorize("hasAuthority(T(com.osekj.vis.model.enums.PermissionType).DELETE_USERS.toGrantedAuthority())")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }
}

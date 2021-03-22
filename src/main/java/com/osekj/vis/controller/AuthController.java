package com.osekj.vis.controller;

import com.osekj.vis.dto.LoginDto;
import com.osekj.vis.dto.TokenDto;
import com.osekj.vis.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/vis-test")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @ResponseStatus(OK)
    public TokenDto login(@RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }
}

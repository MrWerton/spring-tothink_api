package com.notrew.tothink.modules.account.controllers;


import com.notrew.tothink.modules.account.dto.AuthenticationResponseDto;
import com.notrew.tothink.modules.account.dto.CredentialsDto;
import com.notrew.tothink.modules.account.dto.RegisterDto;
import com.notrew.tothink.modules.account.usecases.AccountUsecase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AccountController {

    private final AccountUsecase useCase;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(
            @RequestBody RegisterDto request
    ) {
        return ResponseEntity.ok(useCase.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> authenticate(
            @RequestBody CredentialsDto request
    ) {
        return ResponseEntity.ok(useCase.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        useCase.refreshToken(request, response);
    }


}
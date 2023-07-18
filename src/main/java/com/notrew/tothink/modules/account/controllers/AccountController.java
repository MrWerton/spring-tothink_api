package com.notrew.tothink.modules.account.controllers;


import com.notrew.tothink.modules.account.dto.AuthenticationResponseDto;
import com.notrew.tothink.modules.account.dto.CredentialsDto;
import com.notrew.tothink.modules.account.dto.RegisterDto;
import com.notrew.tothink.modules.account.entities.User;
import com.notrew.tothink.modules.account.usecases.AccountUsecase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AccountController {

    private final AccountUsecase useCase;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterDto request
    ) {

        try {
            return ResponseEntity.ok(useCase.register(request));
        } catch (BadCredentialsException e) {
            // Handle the case when the user is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user already");

        } catch (Exception e) {
            // Handle other unexpected exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(
            @RequestBody CredentialsDto request
    ) {
        try {
            AuthenticationResponseDto responseDto = useCase.authenticate(request);
            return ResponseEntity.ok(responseDto);
        } catch (NoSuchElementException e) {
            // Handle the case when the user is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not  found");
        } catch (AuthenticationException e) {
            // Handle authentication failure
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("credential invalid");
        } catch (Exception e) {
            // Handle other unexpected exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        useCase.refreshToken(request, response);
    }

    @GetMapping("/user-details")
    public ResponseEntity<User> getUserDetails(@RequestParam(value = "email") String email) {
        System.out.println(email);
        var user = useCase.getUserDetails(email);
        return ResponseEntity.ok(user);

    }


}
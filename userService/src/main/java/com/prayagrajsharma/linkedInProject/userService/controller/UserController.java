package com.prayagrajsharma.linkedInProject.userService.controller;

import com.prayagrajsharma.linkedInProject.userService.dto.LoginRequestDto;
import com.prayagrajsharma.linkedInProject.userService.dto.SignUpRequestDto;
import com.prayagrajsharma.linkedInProject.userService.dto.UserDto;
import com.prayagrajsharma.linkedInProject.userService.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class UserController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        UserDto userDto = authService.signUp(signUpRequestDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto) {
        String token = authService.login(loginRequestDto);
        return ResponseEntity.ok(token);

    }

}

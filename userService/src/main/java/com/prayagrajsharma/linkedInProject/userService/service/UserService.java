package com.prayagrajsharma.linkedInProject.userService.service;

import com.prayagrajsharma.linkedInProject.userService.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
}

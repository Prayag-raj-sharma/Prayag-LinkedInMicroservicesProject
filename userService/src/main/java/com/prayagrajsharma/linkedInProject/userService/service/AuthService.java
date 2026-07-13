package com.prayagrajsharma.linkedInProject.userService.service;

import com.prayagrajsharma.linkedInProject.userService.dto.LoginRequestDto;
import com.prayagrajsharma.linkedInProject.userService.dto.SignUpRequestDto;
import com.prayagrajsharma.linkedInProject.userService.dto.UserDto;
import com.prayagrajsharma.linkedInProject.userService.entity.User;
import com.prayagrajsharma.linkedInProject.userService.event.UserCreatedEvent;
import com.prayagrajsharma.linkedInProject.userService.exception.BadRequestException;
import com.prayagrajsharma.linkedInProject.userService.repository.UserRepository;
import com.prayagrajsharma.linkedInProject.userService.utils.BCrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final KafkaTemplate<Long, UserCreatedEvent> userCreatedEventKafkaTemplate;

    public UserDto signUp(SignUpRequestDto signUpRequestDto) {
        log.info("Signup a user with email: {}", signUpRequestDto.getEmail());
        // check if the user is present with this email
        boolean isUserPresent = userRepository.existsByEmail(signUpRequestDto.getEmail());

        if(isUserPresent) throw new BadRequestException("User is already present with this email: "+ signUpRequestDto.getEmail());

        User user = modelMapper.map(signUpRequestDto, User.class);
        user.setPassword(BCrypt.hash(signUpRequestDto.getPassword()));
        user = userRepository.save(user);

        UserCreatedEvent userCreatedEvent = UserCreatedEvent.builder()
                .userId(user.getId())
                .name(user.getName())
                .build();

        userCreatedEventKafkaTemplate.send("user-created_topic", userCreatedEvent);
        return modelMapper.map(user, UserDto.class);
    }

    public String login(LoginRequestDto loginRequestDto) {
        log.info("Login a user with email: {}", loginRequestDto.getEmail());
        // check if the user is present with this email
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(()-> new BadRequestException("Incorrect email or password.."));

        boolean passwordMatched = BCrypt.match(loginRequestDto.getPassword(), user.getPassword());

        if(!passwordMatched) {
            throw new BadRequestException("Incorrect email or password..Please try again..");
        }

        return jwtService.generateAccessToken(user);
    }
}

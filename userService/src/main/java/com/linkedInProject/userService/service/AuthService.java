package com.linkedInProject.userService.service;

import com.linkedInProject.userService.dto.LoginRequestDto;
import com.linkedInProject.userService.dto.SignUpRequestDto;
import com.linkedInProject.userService.dto.UserDto;
import com.linkedInProject.userService.entity.User;
import com.linkedInProject.userService.exception.BadRequestException;
import com.linkedInProject.userService.exception.ResourceNotFoundException;
import com.linkedInProject.userService.repository.UserRepository;
import com.linkedInProject.userService.utils.BCrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JWTService jwtService;

    public UserDto signUp(SignUpRequestDto signUpRequestDto) {
        log.info("signup a user with email {}",signUpRequestDto.getEmail());
        boolean exists=userRepository.existsByEmail(signUpRequestDto.getEmail());
        if(exists) {
            throw new BadRequestException("User already exists");
        }
        User user=modelMapper.map( signUpRequestDto,User.class);
        user.setPassword(BCrypt.hash(signUpRequestDto.getPassword()));
        user=userRepository.save(user);
        return modelMapper.map(user,UserDto.class);
    }

    public String login(LoginRequestDto loginRequestDto) {
        log.info("login a user with email {}",loginRequestDto.getEmail());
        User user=userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(()-> new BadRequestException("Incorrect email or password"));
        Boolean isPasswordMatch=BCrypt.match(loginRequestDto.getPassword(),user.getPassword());
        if(!isPasswordMatch) {
            throw new BadRequestException("Incorrect email or password");
        }
        return jwtService.generateAccessToken(user);

    }
}

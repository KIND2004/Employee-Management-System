package com.novatechzone.web.domain.security.service;

import com.novatechzone.web.domain.security.entity.User;
import com.novatechzone.web.domain.security.domain.UserData;
import com.novatechzone.web.domain.security.entity.UserRole;
import com.novatechzone.web.domain.security.dto.LogInDTO;
import com.novatechzone.web.domain.security.dto.AuthResponseDTO;
import com.novatechzone.web.domain.security.dto.UserDTO;
import com.novatechzone.web.domain.security.repos.UserRepository;
import com.novatechzone.web.domain.security.util.JwtTokenUtil;
import com.novatechzone.web.dto.ApplicationResponseDTO;
import com.novatechzone.web.exception.ApplicationCustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    public ApplicationResponseDTO signup(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "USERNAME_ALREADY_EXIST", "Username Already Exist");
        } else {
            userRepository.save(
                    User.builder()
                            .name(userDTO.getName())
                            .username(userDTO.getUsername())
                            .password(passwordEncoder.encode(userDTO.getPassword()))
                            .userRole(UserRole.USER)
                            .build()
            );
            return new ApplicationResponseDTO(HttpStatus.CREATED, "SIGNUP_SUCCESS", "Sign Up Success");
        }
    }

    public AuthResponseDTO login(LogInDTO logInDTO) {
        Optional<User> optionalUser = userRepository.findByUsername(logInDTO.getUsername());
        if (optionalUser.isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_USERNAME", "Invalid Username");
        } else {
            User user = optionalUser.get();
            if (!passwordEncoder.matches(logInDTO.getPassword(), user.getPassword())) {
                throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_PASSWORD", "Invalid Password");
            }
            String accessToken = jwtTokenUtil.generateAccessToken(user);
            String refreshToken = jwtTokenUtil.generateRefreshToken(user);
            return new AuthResponseDTO(HttpStatus.OK, "LOGIN_SUCCESS", "Login Success", accessToken, refreshToken);
        }
    }

    public static String getCurrentUser() {
        try {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            if (securityContext != null && securityContext.getAuthentication() != null) {
                Object principal = securityContext.getAuthentication().getPrincipal();
                if (principal instanceof UserData userData) {
                    return userData.getUsername();
                } else {
                    throw new ApplicationCustomException(HttpStatus.UNAUTHORIZED, "INVALID_PRINCIPAL", "Invalid Principal");
                }
            } else {
                throw new ApplicationCustomException(HttpStatus.UNAUTHORIZED, "SECURITY_CONTEXT_IS_NULL", "Security Context is Null");
            }
        } catch (Exception e) {
            throw new ApplicationCustomException(HttpStatus.UNAUTHORIZED, "INVALID_USER", e.getMessage());
        }
    }

    public AuthResponseDTO generateRefreshToken(String refreshToken) {
        if (jwtTokenUtil.validateToken(refreshToken)) {
            String username = jwtTokenUtil.getUsernameFromToken(refreshToken);
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ApplicationCustomException(HttpStatus.BAD_REQUEST, "USER_NOT_FOUND", "User not Found"));
            String accessToken = jwtTokenUtil.generateAccessToken(user);
            String newRefreshToken = jwtTokenUtil.generateRefreshToken(user);
            return new AuthResponseDTO(HttpStatus.OK, "NEW_ACCESS_TOKEN_&_NEW_REFRESH_TOKEN", "New Access & Refresh Token", accessToken, newRefreshToken);
        }
        throw new ApplicationCustomException(HttpStatus.UNAUTHORIZED, "INVALID_REFRESH_TOKEN", "Invalid Refresh Token");
    }
}

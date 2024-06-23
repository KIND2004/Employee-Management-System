package com.novatechzone.web.domain.user;

import com.novatechzone.web.domain.security.entity.User;
import com.novatechzone.web.domain.security.entity.UserRole;
import com.novatechzone.web.domain.security.dto.UserDTO;
import com.novatechzone.web.domain.security.repos.UserRepository;
import com.novatechzone.web.domain.security.service.AuthService;
import com.novatechzone.web.dto.ApplicationResponseDTO;
import com.novatechzone.web.exception.ApplicationCustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public ApplicationResponseDTO updateUser(UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findByUsername(AuthService.getCurrentUser())
                .orElseThrow(() -> new ApplicationCustomException(HttpStatus.BAD_REQUEST, "USER_NOT_FOUND", "User Not Found"));

        user.setName(userUpdateDTO.getName());

        userRepository.findByUsername(userUpdateDTO.getUsername()).ifPresent(existingUser -> {
            if (!user.getUsername().equals(userUpdateDTO.getUsername())) {
                throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "EMAIL_ALREADY_EXISTS", "Email Already Exists");
            }
        });

        user.setUsername(userUpdateDTO.getUsername());
        userRepository.save(user);

        return new ApplicationResponseDTO(HttpStatus.OK, "USER_UPDATED_SUCCESSFULLY!", "User Updated Successfully!");
    }

    public User findByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new ApplicationCustomException(HttpStatus.UNAUTHORIZED, "USER_NOT_FOUND", "User not found");
        }
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAllByUserRole(UserRole.USER);
        if (users.isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.NOT_FOUND, "NO_USERS_FOUND", "No Users Found");
        }
        return users;
    }
}
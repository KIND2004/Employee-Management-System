package com.novatechzone.web.domain.security.repos;

import com.novatechzone.web.domain.security.entity.User;
import com.novatechzone.web.domain.security.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndPassword(String username, String password);

    List<User> findAllByUserRole(UserRole userRole);
}

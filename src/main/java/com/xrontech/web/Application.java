package com.xrontech.web;

import com.xrontech.web.domain.department.Department;
import com.xrontech.web.domain.department.DepartmentRepository;
import com.xrontech.web.domain.job.JobRole;
import com.xrontech.web.domain.job.JobRoleRepository;
import com.xrontech.web.domain.security.entity.User;
import com.xrontech.web.domain.security.entity.UserRole;
import com.xrontech.web.domain.security.repos.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@SpringBootApplication
public class Application {
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final JobRoleRepository jobRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void init() {

        Department department = departmentRepository.findByName("Admin").orElseGet(() -> departmentRepository.save(
                Department.builder()
                        .name("Admin")
                        .build()
        ));

        JobRole jobRole = jobRoleRepository.findByTitle("Admin").orElseGet(() -> jobRoleRepository.save(
                        JobRole.builder()
                                .title("Admin")
                                .salary(0.0)
                                .departmentId(department.getId())
                                .build()
                )
        );


        if (userRepository.findByUsername("abdullah@xrontech.com").isEmpty()) {
            userRepository.save(
                    User.builder()
                            .name("Abdullah")
                            .username("abdullah@xrontech.com")
                            .mobile("0771414818")
                            .password(passwordEncoder.encode("P@55w0rd"))
                            .active(true)
                            .delete(false)
                            .userRole(UserRole.ADMIN)
                            .jobRoleId(jobRole.getId())
                            .build()
            );
        }
    }
}

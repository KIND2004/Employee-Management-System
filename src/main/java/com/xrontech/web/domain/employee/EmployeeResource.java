package com.xrontech.web.domain.employee;

import com.xrontech.web.domain.security.entity.User;
import com.xrontech.web.dto.ApplicationResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/employee")
public class EmployeeResource {
    private final EmployeeService employeeService;
    @PostMapping("/create-employee")
    public ResponseEntity<ApplicationResponseDTO> createUser(@Valid @RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeService.createUser(employeeDTO));
    }

    @GetMapping("/get")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(employeeService.getUsers());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(employeeService.getUser(id));
    }

    @PutMapping("/change-status/{id}")
    public ResponseEntity<ApplicationResponseDTO> changeStatus(@PathVariable("id") Long id) {
        return ResponseEntity.ok(employeeService.changeStatus(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApplicationResponseDTO> deleteUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(employeeService.deleteUser(id));
    }
}

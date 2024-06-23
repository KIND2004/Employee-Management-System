package com.xrontech.web.domain.department;

import com.xrontech.web.dto.ApplicationResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/department")
public class DepartmentResource {
    private final DepartmentService departmentService;

    @PostMapping("/create")
    public ResponseEntity<ApplicationResponseDTO> createDepartment(@Valid @RequestBody DepartmentDTO departmentDTO) {
        return ResponseEntity.ok(departmentService.createDepartment(departmentDTO));
    }

    @GetMapping("/get")
    public ResponseEntity<List<Department>> getDepartments() {
        return ResponseEntity.ok(departmentService.getDepartments());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Department> getDepartment(@PathVariable("id") Long id) {
        return ResponseEntity.ok(departmentService.getDepartment(id));
    }

    @GetMapping("/get-by-name/{name}")
    public ResponseEntity<Department> getDepartment(@PathVariable("name") String name) {
        return ResponseEntity.ok(departmentService.getDepartment(name));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApplicationResponseDTO> updateDepartment(@PathVariable("id") Long id, @Valid @RequestBody DepartmentDTO departmentDTO) {
        return ResponseEntity.ok(departmentService.updateDepartment(id, departmentDTO));
    }
}

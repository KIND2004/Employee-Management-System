package com.xrontech.web.domain.department;

import com.xrontech.web.dto.ApplicationResponseDTO;
import com.xrontech.web.exception.ApplicationCustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public ApplicationResponseDTO createDepartment(DepartmentDTO departmentDTO) {
        if (departmentRepository.findByName(departmentDTO.getName()).isPresent()) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "DEPARTMENT_NAME_ALREADY_EXIST", "Department Name Already Exist");
        }
        departmentRepository.save(Department.builder()
                .name(departmentDTO.getName())
                .build());
        return new ApplicationResponseDTO(HttpStatus.CREATED, "NEW_DEPARTMENT_CREATED_SUCCESSFULLY!", "New Department Created Successfully!");
    }

    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartment(Long id) {
        return departmentRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, "INVALID_DEPARTMENT_ID", "Invalid Department Id"));
    }

    public Department getDepartment(String name) {
        return departmentRepository.findByName(name).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, "INVALID_DEPARTMENT_NAME", "Invalid Department Name"));
    }

    public ApplicationResponseDTO updateDepartment(Long id, DepartmentDTO departmentDTO) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, "INVALID_DEPARTMENT", "Invalid Department"));
        department.setName(departmentDTO.getName());
        departmentRepository.save(department);
        return new ApplicationResponseDTO(HttpStatus.CREATED, "NEW_DEPARTMENT_UPDATED_SUCCESSFULLY!", "New Department Updated Successfully!");
    }
}

package com.xrontech.web.domain.job;

import com.xrontech.web.dto.ApplicationResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/job-role")
public class JobRoleResource {
    private final JobRoleService jobRoleService;

    @PostMapping("/create")
    public ResponseEntity<ApplicationResponseDTO> createJobRole(@Valid @RequestBody JobRoleDTO jobRoleDTO) {
        return ResponseEntity.ok(jobRoleService.createJobRole(jobRoleDTO));
    }

    @GetMapping("/get")
    public ResponseEntity<List<JobRole>> getJobRoles() {
        return ResponseEntity.ok(jobRoleService.getJobRoles());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<JobRole> getJobRole(@PathVariable("id") Long id) {
        return ResponseEntity.ok(jobRoleService.getJobRole(id));
    }

    @GetMapping("/get/title/{title}")
    public ResponseEntity<List<JobRole>> getJobRoles(@PathVariable("title") String title) {
        return ResponseEntity.ok(jobRoleService.getJobRoles(title));
    }

    @GetMapping("/get/department/{id}")
    public ResponseEntity<List<JobRole>> getJobRoles(@PathVariable("id") Long departmentId) {
        return ResponseEntity.ok(jobRoleService.getJobRoles(departmentId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApplicationResponseDTO> updateJobRole(@PathVariable("id") Long id, @Valid @RequestBody JobRoleDTO jobRoleDTO) {
        return ResponseEntity.ok(jobRoleService.updateJobRole(id, jobRoleDTO));
    }
}

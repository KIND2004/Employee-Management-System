package com.xrontech.web.domain.leave;

import com.xrontech.web.dto.ApplicationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/leave")
public class LeaveResource {
    private final LeaveService leaveService;

    @PostMapping("/request")
    public ResponseEntity<ApplicationResponseDTO> requestLeave(@RequestBody LeaveDTO leaveDTO) {
        return ResponseEntity.ok(leaveService.requestLeave(leaveDTO));
    }

    @GetMapping("/get")
    public ResponseEntity<List<Leave>> getLeaves() {
        return ResponseEntity.ok(leaveService.getLeaves());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Leave> getLeave(@PathVariable Long id) {
        return ResponseEntity.ok(leaveService.getLeave(id));
    }

    @GetMapping("/get/user/{user-id}")
    public ResponseEntity<List<Leave>> getLeaves(@PathVariable("user-id") Long userId) {
        return ResponseEntity.ok(leaveService.getLeaves(userId));
    }

    @GetMapping("/get/type/{leave-type}")
    public ResponseEntity<List<Leave>> getLeaves(@PathVariable("leave-type") LeaveType leaveType) {
        return ResponseEntity.ok(leaveService.getLeaves(leaveType));
    }

    @GetMapping("/get/type/{user-id}/{leave-type}")
    public ResponseEntity<List<Leave>> getLeaves(@PathVariable("user-id") Long userId, @PathVariable("leave-type") LeaveType leaveType) {
        return ResponseEntity.ok(leaveService.getLeaves(userId, leaveType));
    }

    @GetMapping("/get/status/{leave-status}")
    public ResponseEntity<List<Leave>> getLeaves(@PathVariable("leave-status") LeaveStatus leaveStatus) {
        return ResponseEntity.ok(leaveService.getLeaves(leaveStatus));
    }

    @GetMapping("/get/status/{user-id}/{leave-status}")
    public ResponseEntity<List<Leave>> getLeaves(@PathVariable("user-id") Long userId, @PathVariable("leave-status") LeaveStatus leaveStatus) {
        return ResponseEntity.ok(leaveService.getLeaves(userId, leaveStatus));
    }

    @GetMapping("/get-own")
    public ResponseEntity<List<Leave>> getOwnLeaves() {
        return ResponseEntity.ok(leaveService.getOwnLeaves());
    }

    @GetMapping("/get-own/{id}")
    public ResponseEntity<Leave> getOwnLeave(@PathVariable("id") Long id) {
        return ResponseEntity.ok(leaveService.getOwnLeave(id));
    }

    @GetMapping("/get-own/type/{leave-type}")
    public ResponseEntity<List<Leave>> getOwnLeaves(@PathVariable("leave-type") LeaveType leaveType) {
        return ResponseEntity.ok(leaveService.getOwnLeaves(leaveType));
    }

    @GetMapping("/get-own/status/{leave-status}")
    public ResponseEntity<List<Leave>> getOwnLeaves(@PathVariable("leave-status") LeaveStatus leaveStatus) {
        return ResponseEntity.ok(leaveService.getOwnLeaves(leaveStatus));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ApplicationResponseDTO> updateLeave(@PathVariable("id") Long id, @RequestBody LeaveDTO leaveDTO) {
        return ResponseEntity.ok(leaveService.updateLeave(id, leaveDTO));
    }
    @PutMapping("/cancel/{id}")
    public ResponseEntity<ApplicationResponseDTO> cancelLeave(@PathVariable("id") Long id) {
        return ResponseEntity.ok(leaveService.cancelLeave(id));
    }
    @PutMapping("/approve/{id}")
    public ResponseEntity<ApplicationResponseDTO> approveLeave(@PathVariable("id") Long id) {
        return ResponseEntity.ok(leaveService.approveLeave(id));
    }
}

package com.xrontech.web.domain.complaint;

import com.xrontech.web.dto.ApplicationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/complaint")
public class ComplaintResource {
    private final ComplaintService complaintService;

    @PostMapping("/rise")
    public ResponseEntity<ApplicationResponseDTO> raiseComplaint(@RequestBody ComplaintDTO complaintDTO) {
        return ResponseEntity.ok(complaintService.raiseComplaint(complaintDTO));
    }

    @GetMapping("/get")
    public ResponseEntity<List<Complaint>> getComplaints() {
        return ResponseEntity.ok(complaintService.getComplaints());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Complaint> getComplaint(@PathVariable("id") Long id) {
        return ResponseEntity.ok(complaintService.getComplaint(id));
    }

    @GetMapping("/get/user/{id}")
    public ResponseEntity<List<Complaint>> getComplaints(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(complaintService.getComplaints(userId));
    }

    @GetMapping("/get/type/{complaint-type}")
    public ResponseEntity<List<Complaint>> getComplaints(@PathVariable("complaint-type") ComplaintType complaintType) {
        return ResponseEntity.ok(complaintService.getComplaints(complaintType));
    }

    @GetMapping("/get/type/{user-id}/{complaint-type}")
    public ResponseEntity<List<Complaint>> getComplaints(@PathVariable("user-id") Long userId, @PathVariable("complaint-type") ComplaintType complaintType) {
        return ResponseEntity.ok(complaintService.getComplaints(userId, complaintType));
    }

    @GetMapping("/get/status/{complaint-status}")
    public ResponseEntity<List<Complaint>> getComplaints(@PathVariable("complaint-status") ComplaintStatus complaintStatus) {
        return ResponseEntity.ok(complaintService.getComplaints(complaintStatus));
    }

    @GetMapping("/get/status/{user-id}/{complaint-status}")
    public ResponseEntity<List<Complaint>> getComplaints(@PathVariable("user-id") Long userId, @PathVariable("complaint-status") ComplaintStatus complaintStatus) {
        return ResponseEntity.ok(complaintService.getComplaints(userId, complaintStatus));
    }

    @GetMapping("/get-own")
    public ResponseEntity<List<Complaint>> getOwnComplaints() {
        return ResponseEntity.ok(complaintService.getOwnComplaints());
    }

    @GetMapping("/get-own/{id}")
    public ResponseEntity<Complaint> getOwnComplaint(@PathVariable("id") Long id) {
        return ResponseEntity.ok(complaintService.getOwnComplaint(id));
    }

    @GetMapping("/get-own/type/{complaint-type}")
    public ResponseEntity<List<Complaint>> getOwnComplaints(@PathVariable("complaint-type") ComplaintType complaintType) {
        return ResponseEntity.ok(complaintService.getOwnComplaints(complaintType));
    }

    @GetMapping("/get-own/status/{complaint-status}")
    public ResponseEntity<List<Complaint>> getOwnComplaints(@PathVariable("complaint-status") ComplaintStatus complaintStatus) {
        return ResponseEntity.ok(complaintService.getOwnComplaints(complaintStatus));
    }

    @PutMapping("/update-to-in-progress/{id}")
    public ResponseEntity<ApplicationResponseDTO> updateToInProgress(@PathVariable("id") Long id) {
        return ResponseEntity.ok(complaintService.updateToInProgress(id));
    }

    @PutMapping("/update-to-resolved/{id}")
    public ResponseEntity<ApplicationResponseDTO> updateToResolved(@PathVariable("id") Long id) {
        return ResponseEntity.ok(complaintService.updateToResolved(id));
    }
}
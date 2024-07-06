package com.xrontech.web.domain.complaint;

import com.xrontech.web.domain.security.entity.User;
import com.xrontech.web.domain.security.repos.UserRepository;
import com.xrontech.web.domain.user.UserService;
import com.xrontech.web.dto.ApplicationResponseDTO;
import com.xrontech.web.exception.ApplicationCustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ComplaintService {
    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    private static final String COMPLAINT_NOT_FOUND_CODE = "COMPLAINT_NOT_FOUND";
    private static final String COMPLAINT_NOT_FOUND_MSG = "Complaint not Found!";

    private static final String USER_NOT_FOUND_CODE = "USER_NOT_FOUND";
    private static final String USER_NOT_FOUND_MSG = "User not Found!";

    public ApplicationResponseDTO raiseComplaint(ComplaintDTO complaintDTO) {
        complaintRepository.save(
                Complaint.builder()
                        .employeeId(userService.getCurrentUser().getId())
                        .complaintType(complaintDTO.getComplaintType())
                        .description(complaintDTO.getDescription())
                        .complaintStatus(ComplaintStatus.RAISED)
                        .build()
        );
        return new ApplicationResponseDTO(HttpStatus.CREATED, "COMPLAINT_RAISED_SUCCESSFULLY", "Complaint Raised Successfully!");
    }

    public List<Complaint> getComplaints() {
        return complaintRepository.findAll();
    }

    public Complaint getComplaint(Long id) {
        return complaintRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, COMPLAINT_NOT_FOUND_CODE, COMPLAINT_NOT_FOUND_MSG));
    }

    public List<Complaint> getComplaints(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, USER_NOT_FOUND_CODE, USER_NOT_FOUND_MSG));
        return complaintRepository.findAllByEmployeeId(user.getId());
    }

    public List<Complaint> getComplaints(ComplaintType complaintType) {
        return complaintRepository.findAllByComplaintType(complaintType);
    }

    public List<Complaint> getComplaints(Long userId, ComplaintType complaintType) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, USER_NOT_FOUND_CODE, USER_NOT_FOUND_MSG));
        return complaintRepository.findAllByEmployeeIdAndComplaintType(user.getId(), complaintType);
    }

    public List<Complaint> getComplaints(ComplaintStatus complaintStatus) {
        return complaintRepository.findAllByComplaintStatus(complaintStatus);
    }

    public List<Complaint> getComplaints(Long userId, ComplaintStatus complaintStatus) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, USER_NOT_FOUND_CODE, USER_NOT_FOUND_MSG));
        return complaintRepository.findAllByEmployeeIdAndComplaintStatus(user.getId(), complaintStatus);
    }

    public List<Complaint> getOwnComplaints() {
        return complaintRepository.findAllByEmployeeId(userService.getCurrentUser().getId());
    }

    public Complaint getOwnComplaint(Long id) {
        Complaint complaint = complaintRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, COMPLAINT_NOT_FOUND_CODE, COMPLAINT_NOT_FOUND_MSG));
        if (!complaint.getEmployeeId().equals(userService.getCurrentUser().getId())) {
            throw new ApplicationCustomException(HttpStatus.FORBIDDEN, "UNAUTHORIZED_COMPLAINT", "You are not authorized to access this complaint!");
        }
        return complaint;
    }

    public List<Complaint> getOwnComplaints(ComplaintType complaintType) {
        return complaintRepository.findAllByEmployeeIdAndComplaintType(userService.getCurrentUser().getId(), complaintType);
    }

    public List<Complaint> getOwnComplaints(ComplaintStatus complaintStatus) {
        return complaintRepository.findAllByEmployeeIdAndComplaintStatus(userService.getCurrentUser().getId(), complaintStatus);
    }

    public ApplicationResponseDTO updateToInProgress(Long id) {
        Complaint complaint = complaintRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, COMPLAINT_NOT_FOUND_CODE, COMPLAINT_NOT_FOUND_MSG));

        if (complaint.getComplaintStatus().equals(ComplaintStatus.RAISED)) {
            complaint.setComplaintStatus(ComplaintStatus.IN_PROGRESS);
            complaintRepository.save(complaint);
            return new ApplicationResponseDTO(HttpStatus.OK, "COMPLAINT_STATUS_UPDATED", "Complaint Status Updated!");
        }
        throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "COMPLAINT_ALREADY_" + complaint.getComplaintStatus(), "Complaint is already " + complaint.getComplaintStatus());
    }

    public ApplicationResponseDTO updateToResolved(Long id) {
        Complaint complaint = complaintRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, COMPLAINT_NOT_FOUND_CODE, COMPLAINT_NOT_FOUND_MSG));
        if (complaint.getComplaintStatus().equals(ComplaintStatus.RESOLVED)) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "COMPLAINT_ALREADY_RESOLVED", "Complaint is already resolved!");
        }
        complaint.setComplaintStatus(ComplaintStatus.RESOLVED);
        complaintRepository.save(complaint);
        return new ApplicationResponseDTO(HttpStatus.OK, "COMPLAINT_STATUS_UPDATED", "Complaint Status Updated!");
    }
}
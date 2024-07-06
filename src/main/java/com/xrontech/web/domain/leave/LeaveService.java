package com.xrontech.web.domain.leave;

import com.xrontech.web.domain.security.entity.User;
import com.xrontech.web.domain.user.UserService;
import com.xrontech.web.dto.ApplicationResponseDTO;
import com.xrontech.web.exception.ApplicationCustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LeaveService {
    private final LeaveRepository leaveRepository;
    private final UserService userService;

    private static final String INVALID_LEAVE_ID_CODE = "INVALID_LEAVE_ID";
    private static final String INVALID_LEAVE_ID_MSG = "Invalid Leave Id";

    public ApplicationResponseDTO requestLeave(LeaveDTO leaveDTO) {
        if (leaveDTO.getFromDate().isAfter(leaveDTO.getToDate())) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_LEAVE_DATE", "Leave From Date should be less than Leave To Date");
        }
        User user = userService.getCurrentUser();
        leaveRepository.save(
                Leave.builder()
                        .leaveType(leaveDTO.getLeaveType())
                        .fromDate(leaveDTO.getFromDate())
                        .toDate(leaveDTO.getToDate())
                        .employeeId(user.getId())
                        .leaveStatus(LeaveStatus.PENDING)
                        .build()
        );
        return new ApplicationResponseDTO(HttpStatus.CREATED, "LEAVE_REQUEST_SENT_SUCCESSFULLY", "Leave Request Send Successfully!");
    }

    public List<Leave> getLeaves() {
        return leaveRepository.findAll();
    }

    public Leave getLeave(Long id) {
        return leaveRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, INVALID_LEAVE_ID_CODE, INVALID_LEAVE_ID_MSG));
    }

    public List<Leave> getLeaves(Long userId) {
        return leaveRepository.findAllByEmployeeId(userId);
    }

    public List<Leave> getLeaves(LeaveType leaveType) {
        return leaveRepository.findAllByLeaveType(leaveType);
    }

    public List<Leave> getLeaves(Long userId, LeaveType leaveType) {
        return leaveRepository.findAllByEmployeeIdAndLeaveType(userId, leaveType);
    }

    public List<Leave> getLeaves(LeaveStatus leaveStatus) {
        return leaveRepository.findAllByLeaveStatus(leaveStatus);
    }

    public List<Leave> getLeaves(Long userId, LeaveStatus leaveStatus) {
        return leaveRepository.findAllByEmployeeIdAndLeaveStatus(userId, leaveStatus);
    }

    public List<Leave> getOwnLeaves() {
        User user = userService.getCurrentUser();
        return leaveRepository.findAllByEmployeeId(user.getId());
    }

    public Leave getOwnLeave(Long id) {
        Leave leave = leaveRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, INVALID_LEAVE_ID_CODE, INVALID_LEAVE_ID_MSG));
        User user = userService.getCurrentUser();
        if (!leave.getEmployeeId().equals(user.getId())) {
            throw new ApplicationCustomException(HttpStatus.FORBIDDEN, "UNAUTHORIZED_LEAVE", "Un Authorized Leave");
        }
        return leave;
    }

    public List<Leave> getOwnLeaves(LeaveType leaveType) {
        User user = userService.getCurrentUser();
        return leaveRepository.findAllByEmployeeIdAndLeaveType(user.getId(), leaveType);
    }

    public List<Leave> getOwnLeaves(LeaveStatus leaveStatus) {
        User user = userService.getCurrentUser();
        return leaveRepository.findAllByEmployeeIdAndLeaveStatus(user.getId(), leaveStatus);
    }

    public ApplicationResponseDTO updateLeave(Long id, LeaveDTO leaveDTO) {
        Leave leave = leaveRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, INVALID_LEAVE_ID_CODE, INVALID_LEAVE_ID_MSG));
        if (leave.getLeaveStatus().equals(LeaveStatus.PENDING)) {
            if (leaveDTO.getFromDate().isAfter(leaveDTO.getToDate())) {
                throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_LEAVE_DATE", "Leave From Date should be less than Leave To Date");
            }
            leave.setLeaveType(leaveDTO.getLeaveType());
            leave.setFromDate(leaveDTO.getFromDate());
            leave.setToDate(leaveDTO.getToDate());
            leaveRepository.save(leave);
            return new ApplicationResponseDTO(HttpStatus.OK, "LEAVE_UPDATED_SUCCESSFULLY", "Leave Updated Successfully!");
        }
        throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "CANNOT_UPDATE_UN_PENDING_LEAVES", "Can't Update Un Pending Leaves");
    }

    public ApplicationResponseDTO cancelLeave(Long id) {
        Leave leave = leaveRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, INVALID_LEAVE_ID_CODE, INVALID_LEAVE_ID_MSG));
        if (leave.getLeaveStatus().equals(LeaveStatus.PENDING)) {
            leave.setLeaveStatus(LeaveStatus.CANCEL);
            leaveRepository.save(leave);
            return new ApplicationResponseDTO(HttpStatus.OK, "LEAVE_CANCELED_SUCCESSFULLY", "Leave Canceled Successfully!");
        } else if (leave.getLeaveStatus().equals(LeaveStatus.CANCEL)) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "LEAVE_ALREADY_CANCELED", "Leave already canceled");
        }
        throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "YOU_CANNOT_CANCEL_APPROVED_OR_REJECTED_LEAVE", "You Cannot Cancel Approved or Rejected Leave");
    }

    public ApplicationResponseDTO approveLeave(Long id) {
        Leave leave = leaveRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, INVALID_LEAVE_ID_CODE, INVALID_LEAVE_ID_MSG));
        if (leave.getLeaveStatus().equals(LeaveStatus.PENDING)) {
            leave.setLeaveStatus(LeaveStatus.APPROVED);
            leaveRepository.save(leave);
            return new ApplicationResponseDTO(HttpStatus.OK, "LEAVE_APPROVED_SUCCESSFULLY", "Leave Approved Successfully!");
        } else if (leave.getLeaveStatus().equals(LeaveStatus.APPROVED)) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "LEAVE_ALREADY_APPROVED", "Leave already approved");
        }
        throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "YOU_CANNOT_APPROVE_A_REJECTED_LEAVE", "You Cannot Approve a Rejected Leave");
    }
}

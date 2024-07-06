package com.xrontech.web.domain.leave;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {
    List<Leave> findAllByEmployeeId(Long userId);

    List<Leave> findAllByLeaveType(LeaveType leaveType);

    List<Leave> findAllByEmployeeIdAndLeaveType(Long userId, LeaveType leaveType);

    List<Leave> findAllByLeaveStatus(LeaveStatus leaveStatus);

    List<Leave> findAllByEmployeeIdAndLeaveStatus(Long userId, LeaveStatus leaveStatus);
}

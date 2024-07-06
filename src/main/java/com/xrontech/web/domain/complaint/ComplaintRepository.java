package com.xrontech.web.domain.complaint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findAllByEmployeeId(Long id);

    List<Complaint> findAllByComplaintType(ComplaintType complaintType);

    List<Complaint> findAllByEmployeeIdAndComplaintType(Long id, ComplaintType complaintType);

    List<Complaint> findAllByComplaintStatus(ComplaintStatus complaintStatus);

    List<Complaint> findAllByEmployeeIdAndComplaintStatus(Long id, ComplaintStatus complaintStatus);
}

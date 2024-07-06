package com.xrontech.web.domain.payroll;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long> {
    List<Payroll> findAllByEmployeeId(Long id);

    List<Payroll> findAllByPayDate(LocalDate payDate);

    List<Payroll> findAllByEmployeeIdAndPayDate(Long id, LocalDate payDate);
}

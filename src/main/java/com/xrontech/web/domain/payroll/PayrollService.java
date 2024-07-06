package com.xrontech.web.domain.payroll;

import com.xrontech.web.domain.security.entity.User;
import com.xrontech.web.domain.security.repos.UserRepository;
import com.xrontech.web.domain.user.UserService;
import com.xrontech.web.dto.ApplicationResponseDTO;
import com.xrontech.web.exception.ApplicationCustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PayrollService {
    private final PayrollRepository payrollRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    private static final String PAYROLL_NOT_FOUND_CODE = "PAYROLL_NOT_FOUND";
    private static final String PAYROLL_NOT_FOUND_MSG = "Payroll not Found!";

    private static final String USER_NOT_FOUND_CODE = "USER_NOT_FOUND";
    private static final String USER_NOT_FOUND_MSG = "User not Found!";

    public ApplicationResponseDTO addPayroll(PayrollDTO payrollDTO) {
        User user = userRepository.findById(payrollDTO.getEmployeeId()).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, USER_NOT_FOUND_CODE, USER_NOT_FOUND_MSG));
        Payroll payroll = new Payroll();
        payroll.setEmployeeId(user.getId());
        payroll.setBonus(payrollDTO.getBonus() == null ? 0.0 : payrollDTO.getBonus());
        payroll.setDeductions(payrollDTO.getDeductions() == null ? 0.0 : payrollDTO.getDeductions());
        payroll.setNetPay(getNetPay(user.getJobRole().getSalary(), payrollDTO.getBonus(), payrollDTO.getDeductions()));
        payroll.setPayDate(payrollDTO.getPayDate());
        payrollRepository.save(payroll);
        return new ApplicationResponseDTO(HttpStatus.CREATED, "PAYROLL_ADDED", "Payroll Added!");
    }

    public List<Payroll> getPayrolls() {
        return payrollRepository.findAll();
    }

    public Payroll getPayroll(Long id) {
        return payrollRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, PAYROLL_NOT_FOUND_CODE, PAYROLL_NOT_FOUND_MSG));
    }

    public List<Payroll> getPayrolls(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, USER_NOT_FOUND_CODE, USER_NOT_FOUND_MSG));
        return payrollRepository.findAllByEmployeeId(user.getId());
    }

    public List<Payroll> getPayrolls(LocalDate payDate) {
        return payrollRepository.findAllByPayDate(payDate);
    }

    public List<Payroll> getPayrolls(Long userId, LocalDate payDate) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, USER_NOT_FOUND_CODE, USER_NOT_FOUND_MSG));
        return payrollRepository.findAllByEmployeeIdAndPayDate(user.getId(), payDate);
    }

    public List<Payroll> getOwnPayrolls() {
        return payrollRepository.findAllByEmployeeId(userService.getCurrentUser().getId());
    }

    public Payroll getOwnPayroll(Long id) {
        Payroll payroll = payrollRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, PAYROLL_NOT_FOUND_CODE, PAYROLL_NOT_FOUND_MSG));
        if (!payroll.getEmployeeId().equals(userService.getCurrentUser().getId())) {
            throw new ApplicationCustomException(HttpStatus.FORBIDDEN, "UNAUTHORIZED_PAYROLL", "You are not authorized to access this payroll");
        }
        return payroll;
    }

    public List<Payroll> getOwnPayrolls(LocalDate payDate) {
        return payrollRepository.findAllByEmployeeIdAndPayDate(userService.getCurrentUser().getId(), payDate);
    }

    public ApplicationResponseDTO updatePayroll(Long id, PayrollUpdateDTO payrollUpdateDTO) {
        Payroll payroll = payrollRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, PAYROLL_NOT_FOUND_CODE, PAYROLL_NOT_FOUND_MSG));
        payroll.setBonus(payrollUpdateDTO.getBonus() == null ? payroll.getBonus() : payrollUpdateDTO.getBonus());
        payroll.setDeductions(payrollUpdateDTO.getDeductions() == null ? payroll.getDeductions() : payrollUpdateDTO.getDeductions());
        payroll.setNetPay(getNetPay(payroll.getEmployee().getJobRole().getSalary(), payrollUpdateDTO.getBonus(), payrollUpdateDTO.getDeductions()));
        payroll.setPayDate(payrollUpdateDTO.getPayDate());
        payrollRepository.save(payroll);
        return new ApplicationResponseDTO(HttpStatus.CREATED, "PAYROLL_UPDATED", "Payroll Updated!");
    }

    public ApplicationResponseDTO deletePayroll(Long id) {
        Payroll payroll = payrollRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, PAYROLL_NOT_FOUND_CODE, PAYROLL_NOT_FOUND_MSG));
        payrollRepository.delete(payroll);
        return new ApplicationResponseDTO(HttpStatus.OK, "PAYROLL_DELETED", "Payroll Deleted!");
    }

    private Double getNetPay(Double salary, Double bonus, Double deductions) {
        return salary + (bonus == null ? 0.0 : bonus) - (deductions == null ? 0.0 : deductions);
    }
}
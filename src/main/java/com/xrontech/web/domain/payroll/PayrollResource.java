package com.xrontech.web.domain.payroll;

import com.xrontech.web.dto.ApplicationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("payroll")
public class PayrollResource {
    private final PayrollService payrollService;

    @PostMapping("/add")
    public ResponseEntity<ApplicationResponseDTO> addPayroll(@RequestBody PayrollDTO payrollDTO) {
        return ResponseEntity.ok(payrollService.addPayroll(payrollDTO));
    }

    @GetMapping("/get")
    public ResponseEntity<List<Payroll>> getPayrolls() {
        return ResponseEntity.ok(payrollService.getPayrolls());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Payroll> getPayroll(@PathVariable("id") Long id) {
        return ResponseEntity.ok(payrollService.getPayroll(id));
    }

    @GetMapping("/get/user/{user-id}")
    public ResponseEntity<List<Payroll>> getPayrolls(@PathVariable("user-id") Long userId) {
        return ResponseEntity.ok(payrollService.getPayrolls(userId));
    }

    @GetMapping("/get/date/{pay-date}")
    public ResponseEntity<List<Payroll>> getPayrolls(@PathVariable("pay-date") LocalDate payDate) {
        return ResponseEntity.ok(payrollService.getPayrolls(payDate));
    }

    @GetMapping("/get/{user-id}/{pay-date}")
    public ResponseEntity<List<Payroll>> getPayrolls(@PathVariable("user-id") Long userId, @PathVariable("pay-date") LocalDate payDate) {
        return ResponseEntity.ok(payrollService.getPayrolls(userId, payDate));
    }

    @GetMapping("/get-own")
    public ResponseEntity<List<Payroll>> getOwnPayrolls() {
        return ResponseEntity.ok(payrollService.getOwnPayrolls());
    }

    @GetMapping("/get-own/{id}")
    public ResponseEntity<Payroll> getOwnPayroll(@PathVariable("id") Long id) {
        return ResponseEntity.ok(payrollService.getOwnPayroll(id));
    }

    @GetMapping("/get-own/date/{pay-date}")
    public ResponseEntity<List<Payroll>> getOwnPayrolls(@PathVariable("pay-date") LocalDate payDate) {
        return ResponseEntity.ok(payrollService.getOwnPayrolls(payDate));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApplicationResponseDTO> updatePayroll(@PathVariable("id") Long id, @RequestBody PayrollUpdateDTO payrollUpdateDTO) {
        return ResponseEntity.ok(payrollService.updatePayroll(id, payrollUpdateDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApplicationResponseDTO> deletePayroll(@PathVariable("id") Long id) {
        return ResponseEntity.ok(payrollService.deletePayroll(id));
    }
}

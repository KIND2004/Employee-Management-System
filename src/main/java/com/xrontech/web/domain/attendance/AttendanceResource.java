package com.xrontech.web.domain.attendance;

import com.xrontech.web.domain.security.entity.User;
import com.xrontech.web.dto.ApplicationResponseDTO;
import com.xrontech.web.exception.ApplicationCustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/attendance")
public class AttendanceResource {
    private final AttendanceService attendanceService;

    @PostMapping("/check-in")
    public ResponseEntity<ApplicationResponseDTO> checkIn() {
        return ResponseEntity.ok(attendanceService.checkIn());
    }

    @GetMapping("/is-check-in")
    public ResponseEntity<Boolean> isCheckIn() {
        return ResponseEntity.ok(attendanceService.isCheckIn());
    }

    @PutMapping("/check-out")
    public ResponseEntity<ApplicationResponseDTO> checkOut() {
        return ResponseEntity.ok(attendanceService.checkOut());
    }

    @GetMapping("/get")
    public ResponseEntity<List<Attendance>> getAttendances() {
        return ResponseEntity.ok(attendanceService.getAttendances());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Attendance> getAttendance(@PathVariable("id") Long id) {
        return ResponseEntity.ok(attendanceService.getAttendance(id));
    }

    @GetMapping("/get/user/{user-id}")
    public ResponseEntity<List<Attendance>> getAttendances(@PathVariable("user-id") Long userId) {
        return ResponseEntity.ok(attendanceService.getAttendances(userId));
    }

    @GetMapping("/get/date/{date}")
    public ResponseEntity<List<Attendance>> getAttendances(@PathVariable("date") LocalDate date) {
        return ResponseEntity.ok(attendanceService.getAttendances(date));
    }

    @GetMapping("/get/date/{user-id}/{date}")
    public ResponseEntity<Attendance> getAttendance(@PathVariable("user-id") Long userId, @PathVariable("date") LocalDate date) {
        return ResponseEntity.ok(attendanceService.getAttendance(userId, date));
    }

    @GetMapping("/get-own")
    public List<Attendance> getOwnAttendances() {
        return attendanceService.getOwnAttendances();
    }

    @GetMapping("/get-own/{id}")
    public Attendance getOwnAttendance(@PathVariable("id") Long id) {
        return attendanceService.getOwnAttendance(id);
    }

    @GetMapping("/get-own/date/{date}")
    public Attendance getOwnAttendance(@PathVariable("date") LocalDate date) {
        return attendanceService.getOwnAttendance(date);
    }
}

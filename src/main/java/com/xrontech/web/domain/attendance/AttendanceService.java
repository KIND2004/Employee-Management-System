package com.xrontech.web.domain.attendance;

import com.xrontech.web.domain.security.entity.User;
import com.xrontech.web.domain.security.repos.UserRepository;
import com.xrontech.web.domain.user.UserService;
import com.xrontech.web.dto.ApplicationResponseDTO;
import com.xrontech.web.exception.ApplicationCustomException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final UserService userService;

    private static final String ATTENDANCE_NOT_FOUND_CODE = "ATTENDANCE_NOT_FOUND";
    private static final String ATTENDANCE_NOT_FOUND_MSG = "Attendance not found!";

    private static final String INVALID_ATTENDANCE_CODE = "INVALID_ATTENDANCE";
    private static final String INVALID_ATTENDANCE_MSG = "Invalid Attendance!";
    private final UserRepository userRepository;

    public ApplicationResponseDTO checkIn() {
        if (isCheckIn()) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "ATTENDANCE_ALREADY_CHECKED_IN", "Attendance already checked in!");
        }
        User user = userService.getCurrentUser();
        attendanceRepository.save(
                new Attendance.AttendanceBuilder()
                        .employeeId(user.getId())
                        .checkInTime(LocalTime.now())
                        .date(LocalDate.now())
                        .build()
        );
        return new ApplicationResponseDTO(HttpStatus.CREATED, "ATTENDANCE_ADDED", "Attendance Added!");
    }

    public boolean isCheckIn() {
        User user = userService.getCurrentUser();
        return attendanceRepository.findByDate(LocalDate.now())
                .map(attendance -> attendance.getEmployeeId().equals(user.getId()) && attendance.getCheckOutTime() == null).orElse(false);
    }

    public ApplicationResponseDTO checkOut() {
        if (isCheckIn()) {
            Optional<Attendance> optionalAttendance = attendanceRepository.findByDate(LocalDate.now());
            if (optionalAttendance.isEmpty()) {
                throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, ATTENDANCE_NOT_FOUND_CODE, ATTENDANCE_NOT_FOUND_MSG);
            }
            Attendance attendance = optionalAttendance.get();
            attendance.setCheckOutTime(LocalTime.now());
            attendanceRepository.save(attendance);
            return new ApplicationResponseDTO(HttpStatus.OK, "ATTENDANCE_CHECKED_OUT", "Attendance checked out!");
        }
        throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, INVALID_ATTENDANCE_CODE, INVALID_ATTENDANCE_MSG);
    }

    public List<Attendance> getAttendances() {
        return attendanceRepository.findAll();
    }

    public Attendance getAttendance(Long id) {
        return attendanceRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.BAD_REQUEST, ATTENDANCE_NOT_FOUND_CODE, ATTENDANCE_NOT_FOUND_MSG));
    }

    public List<Attendance> getAttendances(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApplicationCustomException(HttpStatus.BAD_REQUEST, "USER_NOT_FOUND", "User not found!"));
        return attendanceRepository.findAllByEmployeeId(user.getId());
    }

    public List<Attendance> getAttendances(LocalDate date) {
        return attendanceRepository.findAllByDate(date);
    }

    public Attendance getAttendance(Long userId, LocalDate date) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApplicationCustomException(HttpStatus.BAD_REQUEST, "USER_NOT_FOUND", "User not found!"));
        return attendanceRepository.findByEmployeeIdAndDate(user.getId(), date).orElseThrow(() -> new ApplicationCustomException(HttpStatus.BAD_REQUEST, ATTENDANCE_NOT_FOUND_CODE, ATTENDANCE_NOT_FOUND_MSG));
    }

    public List<Attendance> getOwnAttendances() {
        User user = userService.getCurrentUser();
        return attendanceRepository.findAllByEmployeeId(user.getId());
    }

    public Attendance getOwnAttendance(Long id) {
        User user = userService.getCurrentUser();
        Optional<Attendance> optionalAttendance = attendanceRepository.findById(id);
        return getAttendance(user, optionalAttendance);
    }

    public Attendance getOwnAttendance(LocalDate date) {
        User user = userService.getCurrentUser();
        Optional<Attendance> optionalAttendance = attendanceRepository.findByDate(date);
        return getAttendance(user, optionalAttendance);
    }

    @NotNull
    private Attendance getAttendance(User user, Optional<Attendance> optionalAttendance) {
        if (optionalAttendance.isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, ATTENDANCE_NOT_FOUND_CODE, ATTENDANCE_NOT_FOUND_MSG);
        }
        Attendance attendance = optionalAttendance.get();
        if (!attendance.getEmployeeId().equals(user.getId())) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, INVALID_ATTENDANCE_CODE, INVALID_ATTENDANCE_MSG);
        }
        return attendance;
    }
}

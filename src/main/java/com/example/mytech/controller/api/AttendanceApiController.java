package com.example.mytech.controller.api;

import com.example.mytech.entity.Attendance;
import com.example.mytech.exception.NotFoundException;
import com.example.mytech.model.dto.AttendanceDTO;
import com.example.mytech.model.dto.AttendanceScheduleDTO;
import com.example.mytech.model.dto.ScheduleResponseDTO;
import com.example.mytech.model.request.ChangeAttendanceReq;
import com.example.mytech.repository.AttendanceRepository;
import com.example.mytech.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AttendanceApiController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private AttendanceRepository attendanceRepository;


    // tạo danh sách điểm danh của học viên theo lịch học
    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<?> getUserOfCourseListByScheduleId(@PathVariable String scheduleId) {
        try {
            attendanceService.getUserOfCourseByScheduleId(scheduleId);
            return ResponseEntity.ok("Tạo danh sách điểm danh cho học viên thành công");
        } catch (NotFoundException e) {
            // Xử lý ngoại lệ NotFoundException
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Xử lý ngoại lệ chung
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // lấy ra danh sách học sinh đã tạo
    @GetMapping("/attendance/{scheduleId}")
    public ResponseEntity<?> getUserAndAttendanceBySchedule (@PathVariable String scheduleId) {
        try {
            List<AttendanceDTO> attendanceDTOS = attendanceService.getUserAndAttendanceByScheduleId(scheduleId);
            return ResponseEntity.ok(attendanceDTOS);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // thực hiện chức năng điểm danh

    @PutMapping("/update-attendance/{schedule_id}")
    public ResponseEntity<?> updateAttendance(@PathVariable("schedule_id") String scheduleId, @RequestBody List<AttendanceDTO> attendanceDTOs) {
        try {
            attendanceService.updateAttendance(scheduleId, attendanceDTOs);
            return ResponseEntity.ok("Điểm danh thành công");
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // lấy ra danh sách số buổi mà học sinh đó đi học của khóa học
    @GetMapping("/course/{courseId}/user/{userId}")
    @ResponseBody
    public List<AttendanceScheduleDTO> getAttendanceInfoByCourseIdAndUserId(@PathVariable String courseId, @PathVariable String userId) {
        return attendanceService.getAttendanceInfoByCourseIdAndUserId(courseId, userId);
    }

}

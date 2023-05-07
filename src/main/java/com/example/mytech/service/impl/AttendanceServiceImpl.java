package com.example.mytech.service.impl;

import com.example.mytech.entity.*;
import com.example.mytech.exception.NotFoundException;
import com.example.mytech.model.dto.AttendanceDTO;
import com.example.mytech.model.dto.AttendanceResponseDTO;
import com.example.mytech.model.dto.AttendanceScheduleDTO;
import com.example.mytech.model.dto.ScheduleResponseDTO;
import com.example.mytech.repository.AttendanceRepository;
import com.example.mytech.repository.ScheduleRepository;
import com.example.mytech.repository.UserRepository;
import com.example.mytech.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ScheduleRepository scheduleRepository ;
    @Autowired
    private AttendanceRepository attendanceRepository ;

    @Override
    public void getUserOfCourseByScheduleId(String scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElse(null);
        if (schedule == null) {
            // Xử lý lỗi nếu không tìm thấy Schedule
            throw new RuntimeException("Không tìm thấy lịch học");
        }
        ScheduleResponseDTO scheduleResponseDTO = new ScheduleResponseDTO();
        scheduleResponseDTO.setScheduleId(schedule.getId());
        List<AttendanceResponseDTO> attendanceResponseList = new ArrayList<>();
        List<User> users = userRepository.getUsersByScheduleId(schedule.getId()) ;
        for (User user : users) {
            Attendance attendance = attendanceRepository.findByUserAndSchedule(user, schedule);
            if (attendance == null) {
                // Nếu chưa có trong CSDL, thì mới tạo mới Attendance và lưu vào CSDL
                attendance = new Attendance(false, user, schedule);
                attendanceRepository.save(attendance);
                AttendanceResponseDTO attendanceResponseDTO = new AttendanceResponseDTO();
                attendanceResponseDTO.setUser(user);
                attendanceResponseList.add(attendanceResponseDTO);
            }
            else {
                // Nếu đã tồn tại trong CSDL, không làm gì và chỉ tạo mới AttendanceResponseDTO với thông tin của user
                AttendanceResponseDTO attendanceResponseDTO = new AttendanceResponseDTO();
                attendanceResponseDTO.setUser(user);
                attendanceResponseList.add(attendanceResponseDTO);
            }
        }
        scheduleResponseDTO.setAttendanceResponseList(attendanceResponseList);
    }

    @Override
    public List<User> getUsersByScheduleId(String scheduleId) {
        return userRepository.getUsersByScheduleId(scheduleId);
    }

    @Override
    public List<AttendanceDTO> getUserAndAttendanceByScheduleId(String scheduleId) {

        return attendanceRepository.getUserAndAttendanceByScheduleId(scheduleId);
    }

    @Override
    public void updateAttendance(String scheduleId, List<AttendanceDTO> attendanceDTOs) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy lịch trình"));

        for (AttendanceDTO attendanceDTO : attendanceDTOs) {
            User user = userRepository.findById(attendanceDTO.getId())
                    .orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng"));

            Attendance attendance = attendanceRepository.findByUserAndSchedule(user, schedule);
            if (attendance == null) {
                throw new NotFoundException("Không tìm thấy đối tượng điểm danh");
            }

            attendance.setAttendance(attendanceDTO.isAttendance());
            attendanceRepository.save(attendance);
        }
    }

    @Override
    public List<AttendanceScheduleDTO> getAttendanceInfoByCourseIdAndUserId(String courseId, String userId) {
        return attendanceRepository.getAttendanceInfoByCourseIdAndUserId(courseId, userId);
    }


}

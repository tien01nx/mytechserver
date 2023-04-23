package com.example.mytech.service;

import com.example.mytech.entity.User;
import com.example.mytech.model.dto.AttendanceDTO;
import com.example.mytech.model.dto.AttendanceScheduleDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AttendanceService {

    public void getUserOfCourseByScheduleId (String scheduleId);

    public List<User> getUsersByScheduleId(String scheduleId) ;

    public List<AttendanceDTO> getUserAndAttendanceByScheduleId (String scheduleId) ;

    void updateAttendance(String scheduleId, List<AttendanceDTO> attendanceDTOs);

    public List<AttendanceScheduleDTO> getAttendanceInfoByCourseIdAndUserId(String courseId, String userId);

}

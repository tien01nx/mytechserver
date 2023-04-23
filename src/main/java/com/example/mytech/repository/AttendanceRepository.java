package com.example.mytech.repository;

import com.example.mytech.entity.Attendance;
import com.example.mytech.entity.Schedule;
import com.example.mytech.entity.User;

import com.example.mytech.model.dto.AttendanceDTO;
import com.example.mytech.model.dto.AttendanceScheduleDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance , String> {


    @Query("SELECT new com.example.mytech.model.dto.AttendanceDTO( a.user.id,a.user.name,a.user.email,a.user.image,a.user.dateOfBirth, a.attendance) " +
            "FROM Attendance a WHERE a.schedule.id = :scheduleId")
    List<AttendanceDTO> getUserAndAttendanceByScheduleId(@Param("scheduleId") String scheduleId);

    Attendance findByUserId(String userId);

    List<Attendance> findByUserIdIn(List<String> userIds);


    Attendance findByUserAndSchedule(User student, Schedule schedule);
    @Query("SELECT NEW com.example.mytech.model.dto.AttendanceScheduleDTO(s.day, s.dayOfWeek, s.ca,s.status, a.attendance) "
            + "FROM Attendance a "
            + "JOIN a.schedule s "
            + "JOIN s.course c "
            + "WHERE c.id = :courseId AND a.user.id = :userId")
    List<AttendanceScheduleDTO> getAttendanceInfoByCourseIdAndUserId(@Param("courseId") String courseId, @Param("userId") String userId);

}

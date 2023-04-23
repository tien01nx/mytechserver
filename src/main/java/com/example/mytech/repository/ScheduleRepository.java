package com.example.mytech.repository;

import com.example.mytech.entity.Course;
import com.example.mytech.entity.Schedule;
import com.example.mytech.model.dto.ScheduleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, String> {

    @Query("SELECT new com.example.mytech.model.dto.ScheduleDTO(s.id, c.name , s.course.id) FROM Schedule s JOIN s.course c ")
    Page<ScheduleDTO> findScheduleByCourseName(Pageable pageable);

    List<Schedule> findByCourseId(String courseId);

    List<Schedule> findByCourse(Course course);

    List<Schedule> findByDayOrCourse(Date day, Course course);

    Schedule findByDayAndCourseId (Date day , String courseId) ;


}

package com.example.mytech.controller.api;

import com.example.mytech.entity.Course;
import com.example.mytech.entity.Schedule;
import com.example.mytech.exception.NotFoundException;
import com.example.mytech.model.request.ScheduleReq;
import com.example.mytech.model.request.UpdateScheduleReq;
import com.example.mytech.repository.ScheduleRepository;
import com.example.mytech.service.CourseService;
import com.example.mytech.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;


@Controller
public class ScheduleApiController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private CourseService courseService ;

    @Autowired
    private ScheduleRepository scheduleRepository ;

    // lấy danh sách lịch học của khóa học theo id khóa học
    @GetMapping("/courses/{id}/schedule")
    public ResponseEntity<List<Schedule>> getListScheduleByCourse(@PathVariable("id") String courseId) {
        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            throw new NotFoundException("Khóa học không tồn tại");
        }
        List<Schedule> schedules =  scheduleRepository.findByCourse(course);
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("api/schedule/list")
    public ResponseEntity<List<Schedule>> getListSchedule () {
        List<Schedule> schedules = scheduleService.getListSchedule();
        return ResponseEntity.ok(schedules) ;
    }

    @PostMapping("/api/schedules")
    public ResponseEntity<?> createSchedule (@Valid @RequestBody ScheduleReq req) throws ParseException {
        Schedule schedule = scheduleService.createSchedule(req);
        return ResponseEntity.ok().body(schedule);
    }

    @PutMapping("/api/update/schedules/{id}")
    public ResponseEntity<?> updateSchedule (@PathVariable String id , @RequestBody UpdateScheduleReq req) throws Exception {
        scheduleService.updateSchedule(id, req);
        return ResponseEntity.ok("Cập nhật lịch học thành công");
    }

    // delete schedule api
    @DeleteMapping("/api/delete/schedule/{id}")
    public ResponseEntity<?> deleteCourse (@PathVariable("id") String id) {
        Optional<Schedule> rs = scheduleRepository.findById(id);
        if(!rs.isPresent()) {
            throw new NotFoundException("Không tìm thấy lịch học");
        }
        scheduleService.deleteSchedule(rs.get());
        return ResponseEntity.ok("Xóa thàng công");
    }

    @DeleteMapping("/courses/{courseId}/schedules/{scheduleId}")
    public ResponseEntity<?> deleteCourseSchedule(@PathVariable("courseId") String courseId, @PathVariable("scheduleId") String scheduleId) {
        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            return ResponseEntity.notFound().build();
        }
        Schedule schedule = scheduleService.getScheduleById(scheduleId);
        if (schedule == null) {
            return ResponseEntity.notFound().build();
        }
        course.getSchedules().remove(schedule);
        courseService.saveCourse(course);
        return ResponseEntity.ok().build();
    }

    // giáo viên điểm danh cho học viên

}

package com.example.mytech.controller.api;

import com.example.mytech.entity.Course;
import com.example.mytech.entity.User;
import com.example.mytech.model.request.UserRep;
import com.example.mytech.service.CourseService;
import com.example.mytech.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TeacherApiController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService ;

    // get all user have role teacher
    @GetMapping("/teachers")
    public List<User> getAllTeachers() {
        List<User> allTeacher = teacherService.getUserWithRoleTeacher();
        return allTeacher;
    }
    // create user
    @PostMapping("/admin/teacher")
    public ResponseEntity<?> RegisterUser(@Validated @RequestBody UserRep rep) {
        teacherService.createTeacher(rep) ;
        return ResponseEntity.ok("Đăng ký thành công");
    }
    // update user
    @PutMapping("/admin/update/teacher/{id}")
    public ResponseEntity<?> updateUser (@Validated @PathVariable("id") String id , UserRep rep) {
        teacherService.updateTeacher(id,rep);
        return ResponseEntity.ok("Cập nhật thành công");
    }

    // get list course theo userId have role_teacher
    @GetMapping("/users/{id}/courses")
    public List<Course> findCoursesByTeacherId (@PathVariable("id") String userId) {
        return courseService.findCoursesByTeacherId(userId);
    }
}

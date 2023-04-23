package com.example.mytech.controller.api;

import com.example.mytech.entity.User;

import com.example.mytech.model.dto.UserCourseDTO;
import com.example.mytech.security.CustomUserDetails;
import com.example.mytech.service.UserCourseService;
import com.example.mytech.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class EnrollmentApiController {

    @Autowired
    private UserService userService ;
    @Autowired
    private UserCourseService userCourseService;
    // Học viên đăng ký khóa học , kiểm tra học viên đã đăng ký khóa học này hay chưa
    @PostMapping("/api/enrollCourse/{courseId}")
    public ResponseEntity<?> enrollCourse (@PathVariable String courseId){
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        userService.enrollCourse(user.getId(), courseId);
        return ResponseEntity.status(HttpStatus.OK).body("Đăng ký khóa học thành công");
    }
    // hiện list danh sách học viên đã dăng kí
    @GetMapping("/user/course/{id}")
    public ResponseEntity<List<UserCourseDTO>> getUserCoursesByUserId(@PathVariable String id) {
        List<UserCourseDTO> userCourses = userCourseService.findByUserId(id);
        return ResponseEntity.ok(userCourses);
    }
}

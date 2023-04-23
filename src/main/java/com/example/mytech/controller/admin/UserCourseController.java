package com.example.mytech.controller.admin;

import com.example.mytech.entity.UserCourse;
import com.example.mytech.service.UserCourseService;
import com.example.mytech.websocket.WebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserCourseController {

    @Autowired
    private UserCourseService userCourseService;

    @Autowired
    private WebSocketHandler webSocketHandler;

    // hiện danh sách khóa học và học viên đã đăng ký
    @GetMapping("/admin/user_courses")
    public String getListUserCoursePage(Model model,
                                        @RequestParam(defaultValue = "", required = false) String username,
                                        @RequestParam(defaultValue = "", required = false) String courseName,
                                        @RequestParam(defaultValue = "1", required = false) Integer page) {
        // get list user
        Page<UserCourse> userCourses = userCourseService.findUserCourses(username, courseName, page);
        model.addAttribute("userCourses", userCourses.getContent());
        model.addAttribute("totalPages", userCourses.getTotalPages());
        model.addAttribute("currentPage", userCourses.getPageable().getPageNumber() + 1);
        return "admin/user_course/list_usercourse";
    }

}

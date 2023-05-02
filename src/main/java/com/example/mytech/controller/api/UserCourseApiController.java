package com.example.mytech.controller.api;

import com.example.mytech.model.dto.UserCourseDTO;
import com.example.mytech.model.request.ChangeStatusReq;
import com.example.mytech.repository.UserCourseRepository;
import com.example.mytech.service.UserCourseService;
import com.example.mytech.websocket.WebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class UserCourseApiController {

    @Autowired
    private UserCourseService userCourseService;

    @Autowired
    private UserCourseRepository userCourseRepository;


    @Autowired
    private WebSocketHandler webSocketHandler;

    // admin duyệt user đăng ký khóa học
    @PutMapping("/{id}/status")
    public ResponseEntity<Object> updateStatus(@PathVariable("id") String id, @RequestBody ChangeStatusReq req) {
        userCourseService.updateStatus(id, req);
        return ResponseEntity.ok("Cập nhật thành công");
    }


    // lấy ra user chưa đc duyệt
    @GetMapping("/users/{userId}/status")
    public ResponseEntity<List<UserCourseDTO>> getUserCoursesByStatus(
            @PathVariable("userId") String userId,
            @RequestParam(value = "status", defaultValue = "0") int status) {

        List<UserCourseDTO> userCourses = userCourseService.getUserCoursesByStatus(status, userId);
        return ResponseEntity.ok(userCourses);
    }




    @PutMapping("/notification/{userId}")
    public ResponseEntity<?> updateTokenNotification(@PathVariable String userId, @RequestParam String tokenNotification) {
        userCourseService.updateTokenNotification(userId, tokenNotification);
        return ResponseEntity.ok("TokenNotification updated successfully");
    }
}

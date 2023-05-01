package com.example.mytech.controller.api;


import com.example.mytech.entity.Course;
import com.example.mytech.entity.Rank;
import com.example.mytech.entity.User;
import com.example.mytech.model.request.RankReq;
import com.example.mytech.service.CourseService;
import com.example.mytech.service.RankService;
import com.example.mytech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RankApiController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private RankService rankService;

    // thêm điểm cho sinh viên
    @PostMapping("/course/{courseId}/users/{userId}/grades")
    public ResponseEntity<?> createGrades(@PathVariable("courseId") String courseId,
                                          @PathVariable("userId") String userId,
                                          @RequestBody RankReq req) {

        Course course = courseService.getCourseById(courseId);
        User user = userService.getUserById(userId);
        if (course == null || user == null) {
            return ResponseEntity.notFound().build();
        }
        if (!user.getCourses().contains(course)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not enrolled in the course");
        }
        // cập nhật điểm của học viên
        rankService.addGrade(user, course, req);
        return ResponseEntity.ok("Grades updated successfully");
    }

    //cập nhật điểm cho sinh viên
    @PutMapping("/{course_id}/{user_id}/grades")
    public ResponseEntity<?> updateRank(@PathVariable String course_id, @PathVariable String user_id, @RequestBody RankReq req) {

        // Gọi phương thức updateRank của lớp RankService và trả về kết quả
        Rank updatedRank = rankService.updateRank(course_id, user_id, req);
        return ResponseEntity.ok(updatedRank);
    }

    // lấy danh sách điểm của học viên
    @GetMapping("/rank/{course_id}")
    public ResponseEntity<List<Rank>> getRanksByCourseId(@PathVariable String course_id) {
        // Gọi phương thức getRanksByCourseId của lớp RankService và trả về kết quả
        List<Rank> ranks = rankService.getRanksByCourseId(course_id);
        return ResponseEntity.ok(ranks);
    }


    // xóa điểm của học viên
    @DeleteMapping("/rank/delete/{id}")
    public ResponseEntity<?> deleteRank(@PathVariable("id") String id) {
        rankService.deleteRankById(id);
        return ResponseEntity.ok("Xóa thành công");
    }
}
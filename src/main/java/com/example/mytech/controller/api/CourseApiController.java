package com.example.mytech.controller.api;

import com.example.mytech.entity.Course;
import com.example.mytech.entity.Schedule;
import com.example.mytech.entity.User;
import com.example.mytech.model.dto.CourseDTO;
import com.example.mytech.model.dto.UserCourseDTO;
import com.example.mytech.model.request.CourseRep;
import com.example.mytech.repository.UserRepository;
import com.example.mytech.service.CourseService;
import com.example.mytech.service.ScheduleService;
import com.example.mytech.service.TeacherService;
import com.example.mytech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CourseApiController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService ;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private TeacherService teacherService ;

    //get list course
    @GetMapping ("/admin/course/list")
    public ResponseEntity<Object> getListCourse () {
        List<Course> course = courseService.getListCourse();
        return ResponseEntity.ok(course);
    }

    //add course api
    @PostMapping("/admin/courses")
    public ResponseEntity<?> createCourse(@Valid @RequestBody CourseRep req) {
        Course course = courseService.createCourse(req);
        return ResponseEntity.ok(course);
    }

    // update course api
    @PutMapping("/admin/courses/update/{id}")
    public ResponseEntity<?> updateCourse (@PathVariable("id") String id, @Valid @RequestBody CourseRep rep) {
        courseService.updateCourse(id, rep);
        return ResponseEntity.ok("Cập nhật thành công");
    }

    // delete course api
    @DeleteMapping("/admin/courses/delete/{id}")
    public ResponseEntity<?> deleteCourse (@PathVariable("id") Course id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok("Xóa thàng công");
    }

    // lấy danh sách học viên của khóa học
    @GetMapping("/course/{id}/users/role_user")
    public List<User> getUsersWithRoleUserInCourse(@PathVariable("id") String courseId) {
        return userService.findUsersWithRoleUserInCourse(courseId);
    }

    // lấy danh sách giáo viên của khóa học
    @GetMapping("/course/{id}/users/role_teacher")
    public List<User> findUsersWithRoleTeacherInCourse (@PathVariable("id") String courseId) {
        return userService.findUsersWithRoleTeacherInCourse(courseId);
    }

    // lấy ra khóa học theo id
    @GetMapping("/course/{id}")
    public ResponseEntity<?> getListCourseById (@PathVariable String id){
         Course course = courseService.getCourseById(id);
         return ResponseEntity.ok(course);
    }





    // Lấy danh sách khóa học có giảng viên , lịch học của khóa học đó
    @GetMapping("courses/users/schedules")
    public List<CourseDTO> getCoursesWithTeacherAndSchedule() {
        List<Course> courses = courseService.getListCourse();


        List<CourseDTO> courseDTOs = new ArrayList<>();

        for (Course course : courses) {

            List<User> users = userService.findUsersWithRoleTeacherInCourse(course.getId()) ;

            List<String> teacherNames = new ArrayList<>();
            for (User user : users) {
                teacherNames.add(user.getName());
            }
            List<Schedule> schedules = course.getSchedules();

            // Tạo đối tượng CourseDTO chứa thông tin cần thiết
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setId(course.getId());
            courseDTO.setName(course.getName());
            courseDTO.setDescription(course.getDescription());
            courseDTO.setActive(course.getActive());
            courseDTO.setTotalTime(course.getTotalTime());
            courseDTO.setImage(course.getImage());
            courseDTO.setLevel(course.getLevel());
            courseDTO.setPrice(course.getPrice());
            courseDTO.setPublishedAt(course.getPublishedAt());
            courseDTO.setStatus(course.getStatus());
            courseDTO.setNumberOfSessions(course.getNumberOfSessions());
            courseDTO.setTeacheNames(teacherNames);

            courseDTO.setScheduleList(schedules);




            courseDTOs.add(courseDTO);
        }
        return courseDTOs;
    }
}

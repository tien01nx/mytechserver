package com.example.mytech.service;

import com.example.mytech.entity.Course;
import com.example.mytech.entity.Schedule;
import com.example.mytech.entity.User;
import com.example.mytech.entity.UserCourse;
import com.example.mytech.model.request.CourseRep;
import com.example.mytech.model.request.ScheduleReq;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface CourseService {

    public Course saveCourse(Course course);

    // get list course api
    List<Course> getListCourse () ;

    public Course getCourseById (String id) ;

    // get list course và page
    Page<Course> adminGetListCourses(String id, String name, Integer page);

    // create course
    public Course createCourse (CourseRep rep ) ;

    // update course
    public Course updateCourse (String id , CourseRep rep) ;

    // delete coure theo id
    public void deleteCourse (Course course) ;

    // get list couse by userId have role_teacher
    public List<Course> findCoursesByTeacherId (String userId) ;

    // get list couse by userId iddLogined
    public List<Course> findCoursesByUserId (String userId) ;

    // uploadFile
    public String uploadFile(MultipartFile file) ;

    public byte[] readFile (String fileId) ;

    List<UserCourse> getUserCoursesByCourseId(String courseId);

}

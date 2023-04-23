package com.example.mytech.service.impl;

import com.example.mytech.config.Contant;
import com.example.mytech.entity.*;
import com.example.mytech.exception.BadRequestException;
import com.example.mytech.exception.InternalServerException;
import com.example.mytech.exception.NotFoundException;
import com.example.mytech.model.request.CourseRep;
import com.example.mytech.model.request.ScheduleReq;
import com.example.mytech.repository.CourseRepository;
import com.example.mytech.repository.ScheduleRepository;
import com.example.mytech.repository.UserCourseRepository;
import com.example.mytech.service.*;
import com.github.slugify.Slugify;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private UserCourseRepository userCourseRepository;

    @Override
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public List<Course> getListCourse() {
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(String id) {
        Optional<Course> course = courseRepository.findById(id);
        if (!course.isPresent()) {
            throw new NotFoundException("Course do not exits");
        }
        return course.get();
    }

    @Override
    public Page<Course> adminGetListCourses(String id, String name, Integer page) {
        page--;
        if (page < 0) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, Contant.LIMIT_COURSE, Sort.by("createdAt").descending());
        return courseRepository.findCourseByIdOrNameContaining(id, name, pageable);
    }

    @SneakyThrows
    @Override
    public Course createCourse(CourseRep rep){
        Course course = new Course();

        //check exits name
        if (courseRepository.existsByName(rep.getName())) {
            throw new BadRequestException("Tên khóa học đã tồn tại");
        }
        course.setName(rep.getName());

        // set slug
        Slugify slg = new Slugify();
        course.setSlug(slg.slugify(rep.getName()));

        // set price
        course.setPrice(rep.getPrice());

        course.setDescription(rep.getDescription().replaceAll("<p>|</p>", ""));

        if (rep.getActive() == Contant.PUBLIC_COURSE) {
            // Public post
            if (rep.getDescription().isEmpty()) {
                throw new BadRequestException("Để công khai khóa học vui lòng nhập mô tả ");
            }
            if (rep.getImage().isEmpty()) {
                throw new BadRequestException("Để công khai khóa học vui lòng thêm ảnh ");
            }
            course.setPublishedAt(new Timestamp(System.currentTimeMillis()));
        }
        course.setActive(rep.getActive());

        course.setTotalTime(rep.getTotalTime());

        course.setStatus(rep.getStatus());

        // image of course
        course.setImage(rep.getImage());

        course.setLevel(rep.getLevel());

        course.setNumberOfSessions(rep.getNumberOfSessions());

        course.setAddress(rep.getAddress());

        // get list category by id
        if (rep.getCategory_id().isEmpty()) {
            throw new BadRequestException("Vui lòng chọn danh mục cho khóa học");
        }
        Category category = categoryService.getCategoryById(rep.getCategory_id());
        course.setCategory(category);

        course.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        course.setModifiedAt(new Timestamp(System.currentTimeMillis()));

        User teacher = teacherService.getTeacherById(rep.getTeacher_id());
        course.setUsers(Collections.singleton(teacher));

        try {
            courseRepository.save(course);
            UserCourse userCourse = new UserCourse();
            userCourse.setCourse(course);
            userCourse.setUser(teacher);
            userCourse.setEnrollDate(new Timestamp(System.currentTimeMillis()));
            userCourse.setStatus(1);
            userCourseRepository.save(userCourse);

        } catch (Exception e) {
            throw new InternalServerException("Lỗi khi thêm khóa học");
        }
        return course;
    }

    @SneakyThrows
    @Override
    public Course updateCourse(String id, CourseRep rep) {
        Course course;
        Optional<Course> rs = courseRepository.findById(id);
        course = rs.get();
        if (!rs.isPresent()) {
            throw new NotFoundException("Course do not exits");
        }

        course.setName(rep.getName());
        // set slug
        Slugify slg = new Slugify();
        course.setSlug(slg.slugify(rep.getName()));

        // set price
        course.setPrice(rep.getPrice());

        course.setDescription(rep.getDescription().replaceAll("<p>|</p>", ""));

        if (rep.getActive() == Contant.PUBLIC_COURSE) {

            course.setPublishedAt(new Timestamp(System.currentTimeMillis()));
        }
        course.setActive(rep.getActive());

        course.setStatus(rep.getStatus());
        // image of course
        course.setImage(rep.getImage());

        course.setLevel(rep.getLevel());

        course.setTotalTime(rep.getTotalTime());

        // get list category by id
        if (rep.getCategory_id().isEmpty()) {
            throw new BadRequestException("Vui lòng chọn danh mục cho khóa học");
        }
        Category category = categoryService.getCategoryById(rep.getCategory_id());
        course.setCategory(category);

        course.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        course.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        course.setId(id);
        try {
            courseRepository.save(course);
        } catch (Exception e) {
            throw new InternalServerException(e.getMessage());
        }
        return course;
    }

    @Override
    public void deleteCourse(Course course) {
        courseRepository.delete(course);
    }

    @Override
    public List<Course> findCoursesByTeacherId(String userId) {
        return courseRepository.findCoursesByTeacherId(userId);
    }

    @Override
    public List<Course> findCoursesByUserId(String userId) {
        return courseRepository.findCoursesByUserId(userId);
    }

    @Override
    public String uploadFile(MultipartFile file) {
        return imageService.uploadFile(file);
    }

    @Override
    public byte[] readFile(String fileId) {
        return imageService.readFile(fileId);
    }

    @Override
    public List<UserCourse> getUserCoursesByCourseId(String courseId) {
        return null;
    }

}

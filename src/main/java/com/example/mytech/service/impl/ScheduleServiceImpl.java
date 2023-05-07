package com.example.mytech.service.impl;

import com.example.mytech.config.Contant;
import com.example.mytech.entity.Course;
import com.example.mytech.entity.Schedule;
import com.example.mytech.entity.UserCourse;
import com.example.mytech.exception.InternalServerException;
import com.example.mytech.exception.NotFoundException;
import com.example.mytech.model.dto.ScheduleDTO;
import com.example.mytech.model.request.ScheduleReq;
import com.example.mytech.model.request.UpdateScheduleReq;
import com.example.mytech.notification.NotificationService;
import com.example.mytech.repository.ScheduleRepository;
import com.example.mytech.service.CourseService;
import com.example.mytech.service.ScheduleService;
import com.example.mytech.service.UserCourseService;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

@Component
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository ;

    @Autowired
    private CourseService courseService ;

    @Autowired
    private UserCourseService userCourseService;

    @Override
    public List<Schedule> getListSchedule() {
        return scheduleRepository.findAll();
    }

    @Override
    public Page<ScheduleDTO> findScheduleByCourseName( Integer page) {
        page--;
        if (page < 0) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, Contant.LIMIT_COURSE, Sort.by("day").descending());
        return scheduleRepository.findScheduleByCourseName(pageable);
    }

    @SneakyThrows
    @Override
    public Schedule createSchedule(ScheduleReq req) throws ParseException {

        // Kiểm tra mã khóa học
        if (req.getCourse_id().isEmpty()) {
            throw new NotFoundException("Không tìm thấy khóa học ");
        }

        // Lấy khóa học từ mã khóa học
        Course course = courseService.getCourseById(req.getCourse_id());

        // Tạo đối tượng lịch học mới
        Schedule schedule = new Schedule();
        schedule.setCourse(course);

        // Chuyển đổi ngày học từ chuỗi sang Date
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        Date reqDate = formatter.parse(String.valueOf(req.getDay()));

        Date courseStartDate = formatter.parse(String.valueOf(course.getStartDate()));

        if (reqDate.before(courseStartDate)) {
            throw new IllegalArgumentException("Ngày học không nằm trong thời gian bắt đầu của khóa học. "
                    + courseStartDate);
        }
        schedule.setDay(reqDate);
        schedule.setDayOfWeek(req.getDayOfWeek());
        schedule.setCa(req.getDuration());

        // Lấy số buổi học đã có sẵn
        List<Schedule> existingSchedules = scheduleRepository.findByCourse(course);
        int numberOfExistingSchedules = existingSchedules.size();

        // Tự động thêm số buổi học
        if (req.getNumber() == null) {
            // Kiểm tra xem buổi học đã tồn tại trong danh sách lịch học của khóa học hay chưa
            boolean isExistingSchedule = false;
            for (Schedule existingSchedule : existingSchedules) {
                if (existingSchedule.getNumber() == numberOfExistingSchedules + 1) {
                    isExistingSchedule = true;
                    break;
                }
            }
            if (isExistingSchedule) {
                schedule.setNumber(numberOfExistingSchedules + 2);
            } else {
                schedule.setNumber(numberOfExistingSchedules + 1);
            }
        } else {
            schedule.setNumber(req.getNumber());
        }

        if (req.getStatus() == null) {
            schedule.setStatus(1);
        } else {
            schedule.setStatus(req.getStatus());
        }

        // Kiểm tra lịch học đã tồn tại chưa
        for (Schedule existingSchedule : existingSchedules) {
            String existingDayString = formatter.format(existingSchedule.getDay());
            Date existingDate = formatter.parse(existingDayString);
            if (existingDate.equals(reqDate) && existingSchedule.getNumber() == schedule.getNumber()) {
                throw new IllegalArgumentException("Lịch học đã tồn tại.");
            }
        }
        try {
            scheduleRepository.save(schedule);
        } catch (Exception e) {
            throw new InternalServerException("Tạo lịch học cho khóa học thất bại ");
        }
        return schedule;
    }

    @Autowired
    private NotificationService notificationService;

    @SneakyThrows
    @Override
    public Schedule updateSchedule(String id, UpdateScheduleReq req) {
        Schedule schedule;
        Optional<Schedule> rs = scheduleRepository.findById(id);
        schedule = rs.get();
        if (!rs.isPresent()) {
            throw new NotFoundException("Không tìm thấy lịch học ");
        }
        schedule.setStatus(req.getStatus());
        try {
            scheduleRepository.save(schedule);
        } catch (Exception e) {
            throw new InternalServerException("Cập nhật lịch học cho khóa học thất bại ");
        }


        List<UserCourse> userCourses = userCourseService.getUserCoursesByCourseId(schedule.getCourse().getId());
        String title = "Cập nhật lịch học môn: "+schedule.getCourse().getName() ;
        String body = "Lịch học cho khóa học đã được cập nhật. Vui lòng kiểm tra lại lịch học.";

        if (userCourses != null) {
            for (UserCourse userCourse : userCourses) {
                String token = userCourse.getTokenNotification();
                try {
                    if (token!= null){
                        notificationService.sendNotification(title, body, token);
                    }

                } catch (FirebaseMessagingException e) {
                    e.printStackTrace();
                }
            }
        }

        return schedule;
    }

    @Override
    public void deleteSchedule(Schedule schedule) {
        scheduleRepository.delete(schedule);
    }

    @Override
    public Schedule getScheduleById(String id) {
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        if (!schedule.isPresent()) {
            throw new NotFoundException("Schedule do not exits");
        }
        return schedule.get();

    }

    @Override
    public List<Schedule> getCourseSchedules(String courseId) {
        // Tìm khóa học theo Id
        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            throw new NotFoundException("Không tìm thấy khóa học");
        }
        // Lấy danh sách lịch học theo khóa học
        List<Schedule> scheduleList = scheduleRepository.findByCourse(course);

        return scheduleList;
    }
}

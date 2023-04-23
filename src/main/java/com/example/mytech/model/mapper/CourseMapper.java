package com.example.mytech.model.mapper;

import com.example.mytech.entity.Course;
import com.example.mytech.model.dto.CourseDTO;

public class CourseMapper {

    public static CourseDTO toCourseDto(Course course) {
        // Đang chưa làm gì vs hàm này
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setName(course.getName());
        dto.setDescription(course.getDescription());
        dto.setActive(course.getActive());
        dto.setTotalTime(course.getTotalTime());
        dto.setImage(course.getImage());
        dto.setLevel(course.getLevel());
        dto.setPrice(course.getPrice());
        dto.setPublishedAt(course.getPublishedAt());
        dto.setStatus(course.getStatus());
        dto.setNumberOfSessions(course.getNumberOfSessions());
        return dto;
    }
}

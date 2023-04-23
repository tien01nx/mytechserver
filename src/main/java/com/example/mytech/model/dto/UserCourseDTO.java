package com.example.mytech.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserCourseDTO {

    private String courseId;

    private Timestamp enrollDate;

    private int status;

    private String name;
    private String image;
    private String address;

}

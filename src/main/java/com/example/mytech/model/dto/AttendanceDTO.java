package com.example.mytech.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AttendanceDTO {

    private String id;
    private String name;
    private String emai;
    private String image;
    private Date dateOfBirth;
    private boolean attendance;

}
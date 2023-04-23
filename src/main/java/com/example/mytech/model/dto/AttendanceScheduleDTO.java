package com.example.mytech.model.dto;

import com.example.mytech.entity.CA;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AttendanceScheduleDTO {
    private Date day;
    private DayOfWeek dayOfWeek;
    private CA ca;
    private int status;
    private boolean attendance ;

}
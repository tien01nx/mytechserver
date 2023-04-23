package com.example.mytech.model.request;

import com.example.mytech.entity.CA;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.DayOfWeek;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ScheduleReq {

    private String id;

    private DayOfWeek dayOfWeek;

    @JsonProperty("day")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date day;

    private CA duration;

    private Integer status ;

    private String course_id;
}

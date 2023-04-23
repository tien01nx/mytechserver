package com.example.mytech.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RegisterCourse {

    private String id;

    private String user_id;

    private String course_id;

    @JsonProperty("expired_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date registerAt;
}

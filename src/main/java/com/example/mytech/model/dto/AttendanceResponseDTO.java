package com.example.mytech.model.dto;

import com.example.mytech.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AttendanceResponseDTO {

    private User user ;

    private boolean attendance;
}

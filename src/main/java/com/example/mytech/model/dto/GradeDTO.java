package com.example.mytech.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GradeDTO {
    private String courseId;
    private String userId;
    private float midtermGrades;
    private float finalGrades;
    private float exams;
}
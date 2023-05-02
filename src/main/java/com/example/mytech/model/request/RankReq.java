package com.example.mytech.model.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RankReq {

    private float midtermGrades;

    private float finalGrades;

    private float exams;
    private  float avg;
    private String ranking;


   /* private  String name;
    private  String gender;
    private Date dateOfBirth;
    private String image;*/


}
package com.example.mytech.entity;


import com.example.mytech.model.response.UserRankResponse;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ranks")
public class Rank {
    @GenericGenerator(name = "random_id", strategy = "com.example.mytech.model.custom.RandomIdGenerator")
    @Id
    @GeneratedValue(generator = "random_id")
    private String id;

    @Column(name = "midterm_grades")
    private float midtermGrades ;

    @Column(name = "final_grades")
    private float finalGrades;

    @Column(name = "exams")
    private float exams ;

    @Column(name = "avg")
    private float avg ;

    @Column(name = "ranking")
    private String ranking ;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    @JsonBackReference
    private Course course;


    @Transient
    private UserRankResponse userResponse;

    @JsonGetter("user")
    public UserRankResponse getUserResponse() {
        if (user != null) {
            return new UserRankResponse(user);
        }
        return null;
    }

}
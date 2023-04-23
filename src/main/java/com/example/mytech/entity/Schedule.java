package com.example.mytech.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "schedule")
public class Schedule {

    @GenericGenerator(name = "random_id", strategy = "com.example.mytech.model.custom.RandomIdGenerator")
    @Id
    @GeneratedValue(generator = "random_id")
    private String id;

    @Column(name = "day_of_week" , nullable = false)
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Column(name = "day" , nullable = false)
    private Date day ;

    @Column(name = "ca", nullable = false)
    @Enumerated(EnumType.STRING)
    private CA ca;

    @Column(name = "status" ,columnDefinition = "TINYINT(1)") // 1 học , 0 nghỉ
    private int status ;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    @JsonBackReference
    private Course course;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Attendance> attendanceList = new ArrayList<>();

    public void addAttendance(Attendance attendance) {
        attendanceList.add(attendance);
        attendance.setSchedule(this);
    }

    public void removeAttendance(Attendance attendance) {
        attendanceList.remove(attendance);
        attendance.setSchedule(null);
    }
}

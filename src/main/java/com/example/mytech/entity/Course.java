package com.example.mytech.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "course")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Course {
    @GenericGenerator(name = "random_id", strategy = "com.example.mytech.model.custom.RandomIdGenerator")
    @Id
    @GeneratedValue(generator = "random_id")
    private String id;

    @Column(name = "name" , nullable = false)
    private String name ;

    @Column(name = "slug")
    private String slug;

    @Column(name = "description" , columnDefinition = "TEXT")
    private String description;

    @Column(name = "status" , nullable = false , columnDefinition = "TINYINT(1)")
    private int status;

    @Column(name = "active" ,columnDefinition = "TINYINT(1)")
    private int active;

    @Column (name = "price" , nullable = false)
    private long price ;

    @Column(name = "is_level" , nullable = false)
    private int level;

    @Column(name = "image")
    private String image;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "Number_Of_Sessions" , nullable = false)
    private int numberOfSessions ;

    @Column(name = "address" , nullable = false)
    private String address;

    @Column(name = "modified_at", nullable = false)
    private Timestamp modifiedAt;

    @Column (name = "published_at")
    private Timestamp publishedAt;

    @Column(name = "total_time")
    private String totalTime;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @JsonBackReference
    private Category category;

    @ManyToMany(mappedBy = "courses")
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Schedule> schedules;

    @PreRemove
    public void remove() {
        for (User user : users) {
            user.getCourses().remove(this);
        }
    }
    public Date getStartDate() {
        Instant instant = publishedAt.toInstant();
        Date date = Date.from(instant); // Chuyển đổi từ Instant sang Date
        return date;
    }

}

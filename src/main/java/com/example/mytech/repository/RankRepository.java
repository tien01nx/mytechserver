package com.example.mytech.repository;

import com.example.mytech.entity.Course;
import com.example.mytech.entity.Rank;
import com.example.mytech.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RankRepository extends JpaRepository<Rank, String> {

    public Rank findByUserAndCourse(User user, Course course);

    public Rank findByIdAndUserId(String id, String user_id);

    public List<Rank> findByCourseId (String courseId) ;
}
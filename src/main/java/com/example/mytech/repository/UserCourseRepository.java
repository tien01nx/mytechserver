package com.example.mytech.repository;

import com.example.mytech.entity.User;
import com.example.mytech.entity.UserCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserCourseRepository extends JpaRepository<UserCourse, String> {

    List<UserCourse> findByCourse_Id(String courseId);

    List<UserCourse> findByUser_Id(String id);

    List<UserCourse> findByUser(User user);

    List<UserCourse> findByStatusAndUserId(int status, String userId);


    @Query("SELECT uc FROM UserCourse uc JOIN uc.user u JOIN uc.course c JOIN u.roles r WHERE u.name LIKE %:username% AND r.name = 'ROLE_USER' AND c.name LIKE %:courseName%")
    Page<UserCourse> findUserCourses(@Param("username") String username, @Param("courseName") String courseName, Pageable pageable);

}

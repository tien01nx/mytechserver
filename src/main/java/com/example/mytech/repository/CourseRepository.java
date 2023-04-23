package com.example.mytech.repository;

import com.example.mytech.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {

    // get list course and find name
    Page<Course> findCourseByIdOrNameContaining (String id , String name , Pageable pageable) ;

    boolean existsByName (String name) ;

    // count course of category
    Long countByCategoryId (String category_id);

    // lấy danh sách khóa học theo user id đã đăng nhập
    @Query("SELECT c FROM Course c JOIN UserCourse uc ON c.id = uc.course.id JOIN User u ON u.id = uc.user.id WHERE uc.user.id = :userId")
    List<Course> findCoursesByUserId (@Param("userId") String userId);

    // lấy danh sách khóa học theo user có role_teacher
    @Query("SELECT c FROM Course c JOIN UserCourse uc ON c.id = uc.course.id JOIN User u ON uc.user.id = u.id JOIN u.roles r WHERE u.id = :userId AND r.name = 'ROLE_TEACHER'")
    List<Course> findCoursesByTeacherId (@Param("userId") String userId);


}

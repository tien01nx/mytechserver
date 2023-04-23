package com.example.mytech.repository;

import com.example.mytech.entity.Course;
import com.example.mytech.entity.ERole;
import com.example.mytech.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByEmail(String email);

    boolean existsByName(String name);

    User findByEmail(String email);

    List<User> findByIdAndRoles(String userId, ERole role);

    // lấy danh sách role_user theo course id
    @Query("SELECT u FROM User u JOIN u.courses c JOIN u.roles r WHERE c.id = :courseId AND r.name = 'ROLE_USER'")
    List<User> findUsersWithRoleUserInCourse(@Param("courseId") String courseId);

    // lấy danh sách role_teacher theo course id
    @Query("SELECT u FROM User u JOIN u.courses c JOIN u.roles r WHERE c.id = :courseId AND r.name = 'ROLE_TEACHER'")
    List<User> findUsersWithRoleTeacherInCourse(@Param("courseId") String courseId);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = 'ROLE_TEACHER'")
    List<User> findUsersWithTeacherRole();

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = 'ROLE_USER'")
    List<User> findUsersWithUserRole();

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = 'ROLE_USER' AND (u.name LIKE %:nameStudent% OR u.id = :id)")
    Page<User> findUserByNameContainingOrId(@Param("nameStudent") String nameStudent, @Param("id") String id, Pageable pageable);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = 'ROLE_TEACHER' AND (u.name LIKE %:nameTeacher% OR u.id = :id)")
    Page<User> findTeacherByNameOrId(@Param("nameTeacher") String nameTeacher, @Param("id") String id, Pageable pageable);

    @Query("SELECT u FROM User u " +
            "JOIN u.userCourses uc " +
            "JOIN uc.course c " +
            "JOIN c.schedules s " +
            "JOIN u.roles r " +
            "WHERE s.id = :scheduleId " +
            "AND r.name = 'ROLE_USER'")
    List<User> getUsersByScheduleId(@Param("scheduleId") String scheduleId);
}

package com.example.mytech.service.impl;

import com.example.mytech.config.Contant;
import com.example.mytech.entity.ERole;
import com.example.mytech.entity.Role;
import com.example.mytech.entity.User;
import com.example.mytech.exception.BadRequestException;
import com.example.mytech.exception.InternalServerException;
import com.example.mytech.exception.NotFoundException;
import com.example.mytech.model.request.UserRep;
import com.example.mytech.repository.RoleRepository;
import com.example.mytech.repository.UserRepository;
import com.example.mytech.service.TeacherService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository ;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public Page<User> findTeacherByNameOrId(String id, String nameStudent, Integer page) {
        page--;
        if (page < 0) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, Contant.LIMIT_TEACHER, Sort.by("createdAt").descending());
        return userRepository.findTeacherByNameOrId(id, nameStudent,pageable);
    }

    @SneakyThrows
    @Override
    public User createTeacher(UserRep rep) {
        User teacher = new User();

        teacher.setName(rep.getName());

        teacher.setPassword(encoder.encode(rep.getPassword()));

        // check trung email
        if (userRepository.existsByEmail(rep.getEmail())) {
            throw new BadRequestException("Email đã có trong hệ thống , Vui lòng chọn email khác");
        }
        teacher.setEmail(rep.getEmail());

        teacher.setPhone(rep.getPhone());

        teacher.setGender(rep.getGender());

        teacher.setImage(rep.getImage());

        teacher.setAddress(rep.getAddress());

        teacher.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        teacher.setDateOfBirth(rep.getDateOfBirth());

        teacher.setStatus(true);

        List<String> strRoles = rep.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role teacherRole = roleRepository.findByName(ERole.ROLE_TEACHER).orElseThrow(() -> new RuntimeException("Error : Role is not found."));
            roles.add(teacherRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error : Role is not found"));
                        roles.add(adminRole);
                        break;

                    case "user":
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error : Role is not found"));
                        roles.add(userRole);
                        break;
                    default:
                        Role teacherRole = roleRepository.findByName(ERole.ROLE_TEACHER)
                                .orElseThrow(() -> new RuntimeException("Error : Role is not found"));
                        roles.add(teacherRole);
                }
            });
        }
        teacher.setRoles(roles);
        try {
            userRepository.save(teacher);
            return teacher;
        } catch (Exception e) {
            throw new InternalServerException("Lỗi khi thêm giảng viên ");
        }
    }

    @Override
    public List<User> getUserWithRoleTeacher() {
        return userRepository.findUsersWithTeacherRole();
    }

    @SneakyThrows
    @Override
    public User updateTeacher(String id, UserRep rep) {
        User teacher;
        Optional<User> rs = userRepository.findById(id);
        teacher = rs.get();
        if (!rs.isPresent()) {
            throw new NotFoundException("Teacher do not exits");
        }
        teacher.setName(rep.getName());

        teacher.setEmail(rep.getEmail());

        teacher.setGender(rep.getGender());

        teacher.setImage(rep.getImage());

        teacher.setPhone(rep.getPhone());

        teacher.setDateOfBirth(rep.getDateOfBirth());

        teacher.setAddress(rep.getAddress());

        teacher.setModifiedAt(new Timestamp(System.currentTimeMillis()));

        teacher.setStatus(true);

        List<String> strRoles = rep.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_TEACHER).orElseThrow(() -> new RuntimeException("Error : Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error : Role is not found"));
                        roles.add(adminRole);
                        break;

                    case "user":
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error : Role is not found"));
                        roles.add(userRole);
                        break;
                    default:
                        Role teacherRole = roleRepository.findByName(ERole.ROLE_TEACHER)
                                .orElseThrow(() -> new RuntimeException("Error : Role is not found"));
                        roles.add(teacherRole);
                }
            });
        }
        teacher.setRoles(roles);
        try {
            userRepository.save(teacher);
            return teacher;
        } catch (Exception e) {
            throw new InternalServerException("Lỗi khi cập nhật thông tin giảng viên ");
        }
    }

    @Override
    public void deleteTeacher(User user) {
         userRepository.delete(user);
    }

    @Override
    public User getTeacherById(String id) {
        Optional<User> rs = userRepository.findById(id);
        if (!rs.isPresent()) {
            throw new NotFoundException("Teacher do not exits");
        }
        return rs.get();
    }
}

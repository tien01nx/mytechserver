package com.example.mytech.service;

import com.example.mytech.entity.User;
import com.example.mytech.model.request.UserRep;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TeacherService {

    //get list user find name , id
    public Page<User> findTeacherByNameOrId (String id , String nameStudent , Integer page) ;

    // create teacher
    public User createTeacher (UserRep rep) ;

    //get list user have role teacher
    public List<User> getUserWithRoleTeacher();

    // update user
    public User updateTeacher (String id ,UserRep rep) ;

    public void deleteTeacher (User user );

    public User getTeacherById (String id) ;
}

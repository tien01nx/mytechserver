package com.example.mytech.service;


import com.example.mytech.entity.Course;
import com.example.mytech.entity.User;
import com.example.mytech.model.request.ChangePassWordRep;
import com.example.mytech.model.request.RegisterRep;
import com.example.mytech.model.request.UpdateProfileReq;
import com.example.mytech.model.request.UserRep;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface UserService {

    // get all list user
    List<User> getListUser () ;

    // create user
    public User createUser(UserRep rep);

    public User registerUser (RegisterRep rep) ;

    //get list teacher find name , id
    public Page<User> findUserByNameOrId (String id , String nameTeacher , Integer page) ;

    //get list user have role user
    public List<User> getUserWithRoleUser();

    // update user
    public User updateUser (String id ,UserRep rep) ;

    // delete user theo id
    public void deleteUser (User user) ;

    // find by id
    public User getUserById (String id) ;

    // update profile
    public User updateProfile(User user, UpdateProfileReq req, MultipartFile imageFile) throws IOException;

    // uploadFile
    public String uploadFile(MultipartFile file) ;

    public List<User> findUsersWithRoleUserInCourse (String courseId) ;

    public List<User> findUsersWithRoleTeacherInCourse (String courseId) ;

    public List<Course> enrollCourse (String userId , String courseId) ;

    public byte[] readFile (String fileId) ;



}

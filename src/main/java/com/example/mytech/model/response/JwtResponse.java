package com.example.mytech.model.response;

import com.example.mytech.entity.UserCourse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String id;
    private String name;
    private String email;
    private List<String> roles;

    public JwtResponse(String token, String id, String name, String email, List<String> roles) {
        this.token = token;
        this.type = type;
        this.id = id;
        this.name = name;
        this.email = email;
        this.roles = roles;
    }

    public JwtResponse(String token, String id, String name, String email, List<String> roles, List<UserCourse> userCourses) {
        this.token = token;
        this.id = id;
        this.name = name;
        this.email = email;
        this.roles = roles;
    }

    public JwtResponse(String jwt, String username, String password, List<String> roles) {
    }
}

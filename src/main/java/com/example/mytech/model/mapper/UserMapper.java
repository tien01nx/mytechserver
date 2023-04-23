package com.example.mytech.model.mapper;

import com.example.mytech.entity.User;
import com.example.mytech.model.dto.UserDTO;

public class UserMapper {
    public static UserDTO toUserDto(User user) {
        UserDTO tmp = new UserDTO();
        tmp.setId(user.getId());
        tmp.setName(user.getName());
        tmp.setPhone(user.getPhone());
        tmp.setEmail(user.getEmail());
        tmp.setAddress(user.getAddress());

        return tmp;
    }
}

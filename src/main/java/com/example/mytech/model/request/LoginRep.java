package com.example.mytech.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginRep {

    @NotBlank(message = "Email trống")
    private String email ;

    @NotBlank(message = "Passwrod trống")
    @Size(min = 4, max = 50, message = "Mật khẩu phải chứa từ 4 - 50 ký tự")
    private String password;
}

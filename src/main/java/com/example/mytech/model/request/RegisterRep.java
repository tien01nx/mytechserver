package com.example.mytech.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegisterRep {

    private String id ;

    @NotBlank(message = "Tên không được để trống")
    private String name ;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email  ;

    @NotBlank(message = "Mật khẩu trống")
    @Size(min = 6,max = 50, message = "Mật khẩu phải chứa từ 6-20 ký tự")
    private String password ;

    private List<String> roles ;
}

package com.example.mytech.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChangePassWordRep {

    @NotBlank(message = "Nhập mật khẩu cũ ")
    private String oldpassword ;

    @NotBlank(message = "Nhập mật khẩu mới ")
    @Size(min = 4, max = 50, message = "Mật khẩu phải chứa từ 4 - 50 ký tự")
    private String newpassword ;

    @NotBlank(message = "Nhập lại mật khẩu cũ  ")
    @Size(min = 4, max = 50, message = "Mật khẩu phải chứa từ 4 - 50 ký tự")
    private String retypepassword ;
}

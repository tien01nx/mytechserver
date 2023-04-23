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
public class CategoryRep {

    private String id ;

    @NotBlank(message = "Tên danh mục không được để trống")
    @Size(min = 1, max = 300, message = "Độ dài tên danh mục từ 1 - 300 ký tự")
    private String name ;

}

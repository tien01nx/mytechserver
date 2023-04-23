package com.example.mytech.entity;

public enum ERole {
    ROLE_ADMIN("ADMIN"),
    ROLE_TEACHER("GIẢNG VIÊN"),
    ROLE_USER("HỌC VIÊN");

    public final String label;

    private ERole(String label) {
        this.label = label;
    }
}

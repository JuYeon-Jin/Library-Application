package com.group.libraryapp.dto.user.request;

public class UserCreateRequest {
    private String name;
    private Integer age;  // int 는 null 을 표현할 수 없는 자료형이라

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }
}

package com.group.libraryapp.dto.response;

import com.group.libraryapp.domain.user.User;

public class UserResponse {
    private  long id;
    private String name;
    private int age;

    public UserResponse(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.age = user.getAge();
    }

    // public UserResponse(long id, String name, int age) {
    public UserResponse(long id, User user) {
        this.id = id;
        // this.name = name;
        this.name = user.getName();
        // this.age = age;
        this.age = user.getAge();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}

package com.group.libraryapp.project.controller.user;

import com.group.libraryapp.project.domain.user.User;
import com.group.libraryapp.project.dto.request.UserRequest;
import com.group.libraryapp.project.service.user.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 1. controller 진입
    // 1-1. DTO 필요 (UserRequest 생성, getter / setter)
    //      Controller 에서 getter 가 있는 객체를 반환하면 json이 된다 (스프링이 바꿔줌, RestController 덕분)
    // 1-2. Domain 필요 (생성자)

    // CREATE   * 회원 가입
    @PostMapping("/user")
    public void saveUser(@RequestBody UserRequest request) {

    }

    /*
    // READ   * 회원 전체 목록 조회
    @GetMapping("/user")
    public List<UserResponse> getUsers() {
        return userService.getUsers();
    }

    // UPDATE   * 비밀번호 수정
    @PutMapping("/user")
    public void updateUser(@RequestBody UserUpdateRequest request) {
       userService.updateUser(request);
    }

    // DELETE   * 회원 탈퇴
    @DeleteMapping("/user")
    public void deleteUser(@RequestParam String name) {
        userService.deleteUser(name);
    }
    */

}

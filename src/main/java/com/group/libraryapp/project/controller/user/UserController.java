package com.group.libraryapp.project.controller.user;

import com.group.libraryapp.project.dto.user.UserDTO;
import com.group.libraryapp.project.service.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Iterator;

@Controller
public class UserController {

    // 내 책 목록
    @GetMapping("/user/myBook")
    public void myBookList(Model model) {


    }
/*
    // CREATE   * 회원 가입
    @PostMapping("/join")
    public void saveUser(@RequestBody UserRequest request) {
        userService.saveUser(request);
    }

    // READ   * 회원 전체 목록 조회
    @GetMapping("/admin/userlist")
    public List<UserResponse> getUsers(ModelAndView mav) {
        return userService.getUsers();
        //        mav.setViewName("/post/my-posts");
        //        mav.addObject("posts",postService.allPosts());
    }
*/
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

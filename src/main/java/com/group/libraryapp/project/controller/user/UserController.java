package com.group.libraryapp.project.controller.user;

import com.group.libraryapp.project.dto.user.request.UserRequest;
import com.group.libraryapp.project.dto.user.response.UserResponse;
import com.group.libraryapp.project.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 1. controller 진입
    // 1-1. DTO 필요 (UserRequest 생성, getter / setter)
    //      Controller 에서 getter 가 있는 객체를 반환하면 json이 된다 (스프링이 바꿔줌, RestController 덕분)
    // 1-2. Domain 필요 (생성자)

    // 초기 화면
    @GetMapping("/home")
    public String homepage() {
        return "/view/user/home";
    }

    // 로그인 → 유저 권한 체크 (ADMIN: 책 등록 / USER: 대여 책 목록)
    @PostMapping("/login")
    public String login(@RequestParam("id") String id, @RequestParam("pw") String pw) {

        String privateId = userService.authenticate(id, pw);

        if (userService.isAdmin(privateId)) {
            return "redirect:/admin/bookform";
        }
        return "redirect:/user/mybook";
    }

    // 책 등록 PAGE
    @GetMapping("/admin/bookform")
    public String bookForm() {
        return "/view/admin/bookForm";
    }

    // 내 책 목록
    @GetMapping("/user/mybook")
    public ModelAndView myBookList(ModelAndView mav) {
        mav.setViewName("/view/user/myBook");
        // mav.addObject("posts",postService.allPosts());

        return mav;
    }

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

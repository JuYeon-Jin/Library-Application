package com.group.libraryapp.project.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

    // 대여 도서 목록
    @GetMapping("/myBook")
    public String myBookList() {
        return "view/user/loanHistory";
    }

    // 도서 전체 목록
    @GetMapping("/bookList")
    public String bookList() {
        return "view/user/bookList";
    }

    // 도서 대여 (CREATE)
    @PostMapping("/loanBook")
    public void loanBook() {
    }

    // 도서 반납 (PUT)


/*

    // READ   * 회원 전체 목록 조회
    @GetMapping("/admin/userlist")
    public List<UserResponse> getUsers(ModelAndView mav) {
        return userService.getUsers();
        //        mav.setViewName("/post/my-posts");
        //        mav.addObject("posts",postService.allPosts());
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
